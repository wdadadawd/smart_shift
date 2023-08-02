package com.lsu.controller.seize;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsu.common.R;
import com.lsu.entity.*;
import com.lsu.service.*;
import com.lsu.service.impl.seize.TimedGrabService;
import com.lsu.utils.DateUtils;
import com.lsu.utils.RedisUtils;
import com.lsu.vo.ScheduleVo;
import com.lsu.vo.SeizeVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author zt
 * @create 2023-07-18 19:40
 */
@RestController
public class SeizeOrdersController {
    @Resource
    private SeizeOrdersService seizeOrdersService;

    @Resource
    private TimedGrabService  timedGrabService;

    @Resource
    private SignInFormService signInFormService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ScheduleFormService scheduleFormService;

    @Resource
    private StaffInfoService staffInfoService;

    @Resource
    private SeizeVoService seizeVoService;

    @Resource
    private ScheduleVoService scheduleVoService;

    @Resource
    private TopPremiumsService topPremiumsService;

    @Resource
    private MessageFormService messageFormService;

    @Resource
    private MesUserMapService mesUserMapService;


    /**
     * 发布抢单
     * @param seizeOrders (seizeId,startTime,endTime,maxPremiums,minPremiums,type)status
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PostMapping("/seizeOrders")
    public R<String> publishSeizeOrders(@RequestBody SeizeOrders seizeOrders){
        if (seizeOrdersService.getById(seizeOrders.getSeizeId()).getStatus().equals("已发布"))
            return R.err("单次已经发布过了");
        SeizeVo seizeVo = seizeVoService.getById(seizeOrders.getSeizeId());
        //判断抢单时间是否符合
        if (seizeVo.getScheduleStartTime().before(seizeOrders.getEndTime()))
            return R.err("抢单结束时间不能在班次开始之后");
        seizeOrders.setStatus("已发布");             //设置抢单状态
        boolean result = seizeOrdersService.updateById(seizeOrders);//更新
        if (result && seizeOrders.getType().equals("superior")){
            //发布定时任务
            timedGrabService.createLottery(seizeOrders.getEndTime(),seizeOrders.getSeizeId());
        }
        if (result){
            //发布消息通知
            MessageForm messageForm = new MessageForm("抢单通知", null, null, new Date()
                    , "您有一条新的抢单,开始时间为:" + seizeOrders.getStartTime());
            messageFormService.save(messageForm);
            MesUserMap mesUserMap = new MesUserMap(messageForm.getMessageId(), null);
            for (StaffInfo staffInfo : staffInfoService.getAllStaff()) {
                mesUserMap.setReceiveId(staffInfo.getUserId());
                mesUserMapService.save(mesUserMap);
            }
            return R.success("发布成功");
        }
        return R.err("发布失败");
    }


    /**
     * 抢单
     * @param seizeOrders (seizeId,staffId) ,isAccomplish,status
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","normal"},logical = Logical.OR)
    @PutMapping("/grabSeizeOrders")
    public R<String> grabSeizeOrders(@RequestBody SeizeOrders seizeOrders){
        //获取抢单信息
        SeizeOrders seizeOrdersInfo = seizeOrdersService.getById(seizeOrders.getSeizeId());
        Date startTime = seizeOrdersInfo.getStartTime();        //抢单开始时间
        Date endTime = seizeOrdersInfo.getEndTime();            //抢单结束时间
        Double maxPremiums = seizeOrdersInfo.getMaxPremiums();       //抢单最大金额
        Double minPremiums = seizeOrdersInfo.getMinPremiums();       //抢单最小金额
        Date date = new Date();
        // 1.判断当前时间是否在抢单时间区间内
        if (date.after(startTime) && date.before(endTime)) {
            long timeDif1 = (startTime.getTime() - date.getTime())/1000;        //抢单时间距离开始时间的秒差
            long timeDif2 = (endTime.getTime() - startTime.getTime())/1000;     //抢单结束时间到抢单开始时间的秒差
            //计算最终金额(保留一位小数)
            Double finalPremiums = Math.round(1.0*timeDif1/timeDif2*(maxPremiums-minPremiums) + minPremiums*10)/10.0 + minPremiums;
            //2.判断抢单类型
            if (seizeOrdersInfo.getType().equals("superior")){         //择优
                SeizeCache seizeCache = new SeizeCache(seizeOrders.getSeizeId(), seizeOrders.getStaffId(), date, finalPremiums);
                if (redisUtils.judgeSeizeCache(seizeCache)){
                    return R.err("你已经抢过这单了!");
                }else {
                    //添加到缓存中
                    MessageForm messageForm = new MessageForm("抢单提示", null, null, new Date()
                            , "抢单成功,请耐心等待最终结果");
                    messageFormService.save(messageForm);
                    mesUserMapService.save(new MesUserMap(messageForm.getMessageId(),seizeOrders.getStaffId()));
                    redisUtils.addSeizeCacheAndTime(seizeCache);
                    return R.success("抢单成功");
                }
            }else {                                            //先到先得
                seizeOrders.setTakeTime(new Date());          //设置抢得时间
                seizeOrders.setStatus("已被抢");               //设置班次状态
                seizeOrders.setFinalPremiums(finalPremiums);      //设置最终金额
                seizeOrders.setIsAccomplish(false);
                //更新
                Integer result = seizeOrdersService.updateByIdAndByStaffId(seizeOrders);
                if (result > 0){
                    //发布消息通知
                    MessageForm messageForm = new MessageForm("抢单结果", null, null, new Date()
                            , "抢单成功,注意新增班次并准时完成");
                    messageFormService.save(messageForm);
                    mesUserMapService.save(new MesUserMap(messageForm.getMessageId(),seizeOrders.getStaffId()));
                    //发布签到
                    SignInForm signInForm = new SignInForm(seizeOrders.getStaffId(),seizeOrdersInfo.getScheduleId(),"未签到");
                    signInFormService.save(signInForm);    //保存
                    //更新排班表
                    ScheduleForm scheduleForm = new ScheduleForm(seizeOrdersInfo.getScheduleId(),seizeOrders.getStaffId());
                    scheduleFormService.updateById(scheduleForm);
                    return R.success("抢单成功");
                }
                return R.err("单次已被抢");
            }
        } else {
            // 当前时间不在区间内
            return R.err("抢单未开始或已结束");
        }
    }


    /**
     * 获取抢单的分页信息
     * @param current 页码
     * @param size 条数
     * @param key 关键字
     * @param storeId 门店id
     * @param session 会话
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @GetMapping("/seizeOrdersList")
    public R<Page<SeizeVo>> getSeizeVoPage(@RequestParam Integer current, @RequestParam Integer size, @RequestParam(defaultValue = "") String key
                                    , @RequestParam(required = false) Integer storeId, HttpSession session){
        //对请求做分析(店长发起的/管理员发起的)
        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            Integer userId = (Integer) session.getAttribute("userId");    //获取店长id
            storeId = staffInfoService.getStoreIdByUserId(userId);    //获取店长所在门店id
        }
        Page<SeizeVo> seizeVoPage = seizeVoService.getSeizeVoPage(current, size, key, storeId);
        return R.success(seizeVoPage);
    }



    /**
     * 指派员工
      * @param seizeOrders (seizeId,finalPremiums、staffId)isAccomplish、status、takeTime ,发布签到、更新排班表
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PutMapping("/seizeOrders")
    public R<String> updateSeize(@RequestBody SeizeOrders seizeOrders){
        SeizeVo seizeVo = seizeVoService.getById(seizeOrders.getSeizeId());
        String status = seizeVo.getStatus();
        if ("已结束".equals(status) || "未开始".equals(status) || "未发布".equals(status)){
            seizeOrders.setIsAccomplish(false);
            seizeOrders.setStatus("已指派");
            seizeOrders.setTakeTime(new Date());
            seizeOrdersService.updateById(seizeOrders);
            //发布签到
            SignInForm signInForm = new SignInForm(seizeOrders.getStaffId(),seizeVo.getScheduleId(),"未签到");
            signInFormService.save(signInForm);    //保存
            //更新排班表
            ScheduleForm scheduleForm = new ScheduleForm(seizeVo.getScheduleId(),seizeOrders.getStaffId());
            scheduleFormService.updateById(scheduleForm);
            //发布消息通知
            MessageForm messageForm = new MessageForm("指派通知", null, null, new Date()
                    , "您已被指派,班次开始时间为:" + DateUtils.getDateTime(seizeVo.getScheduleStartTime()) + ",注意查收");
            messageFormService.save(messageForm);
            mesUserMapService.save(new MesUserMap(messageForm.getMessageId(),seizeOrders.getStaffId()));
            return R.success("指派成功");

        }else
            return R.err("指派失败,抢单状态为:" + status);
    }


    /**
     * 获取合适的指派员工
     * @param seizeId 抢单id
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @GetMapping("/fitSeizeStaff")
    public R<List<StaffInfo>> getFitSeizeStaff(@RequestParam Integer seizeId){
        SeizeOrders seizeOrders = seizeOrdersService.getById(seizeId);
        ScheduleVo scheduleVo = scheduleVoService.getById(seizeOrders.getScheduleId());
        List<StaffInfo> suitableStaff = staffInfoService.getSuitableStaff(scheduleVo);
        return R.success(suitableStaff);
    }


    /**
     * 获取员工合适的抢单
     * @param staffId 员工id
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","normal"},logical = Logical.OR)
    @GetMapping("/fitSeize")
    public R<List<SeizeVo>> getFitSeize(Integer staffId){
        List<SeizeVo> seizeList = seizeVoService.getSeizeList(staffInfoService.getById(staffId));
        return R.success(seizeList);
    }


    /**
     * 获取指定月抢单排行榜
     * @param date 日期(年、月)
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","normal"},logical = Logical.OR)
    @GetMapping("/SeizeTopInfos")
    public R<List<SeizeTopInfo>> getSeizeTopInfos(@DateTimeFormat(pattern="yyyy-MM") @RequestParam Date date){
        List<SeizeTopInfo> seizeTopInfos = seizeVoService.getSeizeTopInfos(DateUtils.getDate(date));
        HashMap<Integer, Double> tpMap = new HashMap<>();
        //1.解析排行对应的奖励
        List<TopPremiums> topPremiumsListByDate = topPremiumsService.getTopPremiumsListByDate(date);
        if(topPremiumsListByDate==null || topPremiumsListByDate.size()==0)
            R.success(seizeTopInfos);
        for (TopPremiums tp : topPremiumsListByDate) {
            String ranking = tp.getRanking();          //字符串排名  =====> 1 或 2,6形式
            Double premiums = tp.getPremiums();        //奖励
            if (ranking.contains(",")){
                int i = ranking.indexOf(",");
                int min = Integer.parseInt(ranking.substring(0,i));
                int max = Integer.parseInt(ranking.substring(i+1));
                for (;min<=max;min++){
                    tpMap.put(min,premiums);
                }
            }else{
                tpMap.put(Integer.parseInt(ranking),premiums);
            }
        }
        //2.设置排行和奖励
        int rank = 0;
        for (int i=0;i<seizeTopInfos.size();i++){
            SeizeTopInfo seizeTopInfo = seizeTopInfos.get(i);
            //判断排名是否增加
            if (i==0 || !Objects.equals(seizeTopInfos.get(i - 1).getSeizeCount(), seizeTopInfo.getSeizeCount())){
                rank++;
            }
            //设置排名、排名奖励
            seizeTopInfo.setRanking(rank);
            seizeTopInfo.setRankPremiums(tpMap.get(rank));
        }
        return R.success(seizeTopInfos);
    }
}
