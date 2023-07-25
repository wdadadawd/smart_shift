package com.lsu.controller.schedule;

import com.lsu.common.R;
import com.lsu.entity.*;
import com.lsu.service.*;
import com.lsu.utils.DateUtils;
import com.lsu.utils.RandomUtils;
import com.lsu.vo.ScheduleVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zt
 * @create 2023-03-18 20:51
 * 管理排班
 */
@RestController
public class ScheduleFormController {
    @Resource
    private ScheduleFormService scheduleFormService;     //排班表查询

    @Resource
    private StoreRuleService storeRuleService;           //门店规则事务

    @Resource
    private PassengerFlowService passengerFlowService;    //客流量事务

    @Resource
    private StaffInfoService staffInfoService;            //员工信息事务

    @Resource
    private StoreInfoService storeInfoService;            //门店信息事务

    @Resource
    private ScheduleVoService scheduleVoService;         //排班信息vo

    /**
     * 对从startDate日期开始的一周进行排班   (startDate为某周周一的日期)
     * @param storeId 门店id
     * @return 排班结果
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PostMapping("/generateScheduleForm")
    public R<String> generateScheduleForm(@RequestParam Integer storeId,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam Date startDate){
        // 记录程序开始时间
        long startTime = System.currentTimeMillis();
        if (!"星期一".equals(DateUtils.getToDay(startDate)))
            return R.err("开始时间错误");
        //1.获取门店信息
        StoreInfo storeInfo = storeInfoService.getOneStore(storeId);
        //2.获取门店开启的规则(类型和值)
        List<StoreRule> storeUseRuleList = storeRuleService.getStoreUseRule(storeId);
        //对规则做处理
        Double a = 1.0,b1,b2,c,d,e1,e2,f,g;         //记录规则值
        for (int i=0;i<storeUseRuleList.size();i++){
            StoreRule storeRule = storeUseRuleList.get(i);
            String value = storeRule.getValue();
            switch (storeRule.getType()){
                case "a":a=Double.parseDouble(value);break;                 //每rt个预测客流量需要一个人,默认为1
                case "b":b1= Double.parseDouble(value.substring(0,value.indexOf(",")));
                         b2=Double.parseDouble(value.substring(value.indexOf(",")+1));
                         break;//开店前x个小时必须有员工做开店准备，人数为面积/x
                case "c":c=Double.parseDouble(value);break;//每天关店之后需要x小时做收尾工作
                case "d":d=Double.parseDouble(value);break;//如果没有客流量的时候至少需要x个员工当值
                case "e":e1=Double.parseDouble(value.substring(0,value.indexOf(",")));
                         e2=Double.parseDouble(value.substring(value.indexOf(",")+1));
                         break;//全勤奖励x元，迟到，早退，每分钟扣罚x元
                case "f":f=Double.parseDouble(value);break;//休息时段的时间不能超过x分钟
                case "g":g=Double.parseDouble(value);break;//在午餐或晚餐时间至少需要x个员工当值
            }
        }
        //3.获取门店的可用员工
        List<StaffInfo> staffInfoList = staffInfoService.getStaffListByStore(storeId);
        //4.需排班的日期
        List<Date> dateList = DateUtils.getWeekDay(startDate);    //从startDate开始的一周时间
        //5.获取门店的需排班日期的客流量表
        List<List<PassengerFlow>> lists = passengerFlowService.getDayListPassengerFlowList(dateList, storeId);
        //6.清除之前已排的班次信息
        scheduleFormService.deleteWeekScheduleForm(dateList,storeId);
        //7.创建记录员工周、日工作时间集合
        List<Integer> staffDayWorkTime,staffWeekWorkTime = null;
        //8.对需要排班的日期进行排班
        //(1)遍历准备工作
        Integer staffSum = staffInfoList.size();                //获取员工总人数
        if (staffSum==0)
            return R.err("门店无员工");
        //(2)遍历所有需要排班的日期
        for (int i=0;i<dateList.size();i++){
            //(3)初始化数据
            Date date = dateList.get(i);                                                //本次排班的日期
            staffDayWorkTime = new ArrayList<>(Collections.nCopies(staffSum, 0));   //1每日初始化员工日工作时长
            if (staffWeekWorkTime == null)                                              //2初始化员工周工作时长
                staffWeekWorkTime = new ArrayList<>(Collections.nCopies(staffSum, 0));
            List<PassengerFlow> passengerFlowList = lists.get(i);  //3本次排班日期的预测客流量
            List<Integer> passengerRtList = new ArrayList<>();    //4将预测客流量转换为所需的员工数量
            for (int j=0;j<passengerFlowList.size();j++){
                double v = Math.round(passengerFlowList.get(j).getCalculate() / a);
                passengerRtList.add(j,(int) v);
            }
            for (int j=0;j<passengerRtList.size()-3;j++){           //最少每2小时一个班次
                Integer rt = passengerRtList.get(j);
                if (rt > 0){
                    for (int k=1;k<4;k++){
                        passengerRtList.set(j+k,passengerRtList.get(j+k)-rt);
                    }
                }
            }
            int max = 0;                        //清空后三个时间段
            for (int j=passengerRtList.size()-3;j<passengerRtList.size();j++){
                if (passengerRtList.get(j) > max)
                    max = passengerRtList.get(j);
                passengerRtList.set(j,0);
            }
            passengerRtList.set(passengerRtList.size()-4,max);
//            int sum = 0;                      所需人数粗计
//            for (int j=0;j<passengerRtList.size();j++)
//                sum+=passengerRtList.get(j);
//            System.out.println(sum);
            //排班
            List<Integer> staffScheduleCount = new ArrayList<>(Collections.nCopies(staffSum, 0)); //员工是否能被选中
            int maxCount = 5;                   //最多循环数
            for (int j=0;j<passengerRtList.size();j++){
                for (int k=0;k<passengerRtList.get(j);k++){          //需要passengerRtList.get(j)个人
                    int random = RandomUtils.getRandom(staffWeekWorkTime);    //根据轮盘法获取员工在集合中的位置
                    //循环直到对应员工符合要求 (最多循环四次)
                    int count = 1;
                    //count为0才能被选中
                    while (!(staffDayWorkTime.get(random)<=6&&staffWeekWorkTime.get(random)<=38) || 0 != staffScheduleCount.get(random)) {
                        if (count == maxCount)
                            break;
                        random = RandomUtils.getRandom(staffWeekWorkTime);
                        count++;
                    }
//                    System.out.print("," + random);
                    Integer staffId;             //员工id(null空班次(count==5))
                    if (count == maxCount)          //判断是否已找到合适的员工
                        staffId = null;
                    else{
                        staffScheduleCount.set(random,staffScheduleCount.get(random)+4);//员工是否能被选中时间
                        staffDayWorkTime.set(random,staffDayWorkTime.get(random)+2);   //员工日时长增加
                        staffWeekWorkTime.set(random,staffWeekWorkTime.get(random)+2); //员工周时长增加
                        staffId = staffInfoList.get(random).getUserId();
                    }
                    //创建班次信息
                    ScheduleForm scheduleForm = new ScheduleForm(storeId, date, passengerFlowList.get(j).getStartTime()
                            , passengerFlowList.get(j + 3).getEndTime(), staffId);
//                    System.out.println(scheduleForm);
                    scheduleFormService.save(scheduleForm);       //保存班次信息
                }
                //重置员工是否能被选中,每轮减一
                for (int k = 0;k<staffScheduleCount.size();k++){
                    if (staffScheduleCount.get(k) > 0){
                        staffScheduleCount.set(k,staffScheduleCount.get(k)-1);
                    }
                }
            }
//            System.out.println();
        }
        //程序运行时间
        System.out.println("程序运行时间：" + (System.currentTimeMillis() - startTime) + " 毫秒");
        return R.success("排班成功");
    }


    /**
     * 获取指定门店某日的班次信息
     * @param storeId 门店id
     * @param date 日期
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @GetMapping("/dayScheduleForm")
    public R<List<ScheduleVo>> getDayScheduleForm(@RequestParam Integer storeId, @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam Date date){
        List<ScheduleVo> dayScheduleFormVo = scheduleVoService.getDayScheduleFormVo(storeId, date);
        return R.success(dayScheduleFormVo);
    }

    /**
     * 获取指定门店某周的班次信息
     * @param storeId 门店id
     * @param startDate 开始日期
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @GetMapping("/weekScheduleForm")
    public R<List<List<ScheduleVo>>> getWeekScheduleForm(@RequestParam Integer storeId,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam Date startDate){
        if (!"星期一".equals(DateUtils.getToDay(startDate)))
            return R.err("开始时间错误");
        List<List<ScheduleVo>> weekScheduleFormVo = scheduleVoService.getWeekScheduleFormVo(storeId, startDate);
        return R.success(weekScheduleFormVo);
    }


    /**
     * 员工获取指定日期的班次
     * @param staffId 员工id
     * @param date 日期
     * @return
     */
    @RequiresRoles(value = {"normal","admin","shopowner"},logical = Logical.OR)
    @GetMapping("/staffDayScheduleForm")
    public R<List<ScheduleVo>> getStaffScheduleForm(@RequestParam Integer staffId,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam Date date){
        List<ScheduleVo> staffScheduleFormVo = scheduleVoService.getStaffScheduleFormVo(staffId, date);
        return R.success(staffScheduleFormVo);
    }


    /**
     * 添加一个班次
     * @param scheduleForm (staffId、date、startTime、endTime、storeId)
     * @return 添加结果
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PostMapping("/scheduleForm")
    public R<String> insertScheduleForm(@RequestBody ScheduleForm scheduleForm){
        Date startTime = scheduleForm.getStartTime();
        Date endTime = scheduleForm.getEndTime();
        if ((endTime.getTime() - startTime.getTime())/(1000 * 60 * 60) != 2)
            return R.err("开始结束时间错误");
        if (scheduleFormService.save(scheduleForm))
            return R.success("添加成功");
        return R.err("添加失败");
    }


    /**
     * 删除一个班次
     * @param scheduleId 班次id
     * @return 删除结果
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @DeleteMapping("/scheduleForm")
    public R<String> deleteScheduleForm(@RequestParam Integer scheduleId){
        if (scheduleFormService.removeById(scheduleId))
            return R.success("删除成功");
        return R.err("删除失败");
    }


    /**
     * 更换班次人员
     * @param scheduleForm (scheduleId、staffId)
     * @return 更换结果
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PutMapping("/scheduleForm")
    public R<String> updateScheduleForm(@RequestBody ScheduleForm scheduleForm){
        if (scheduleFormService.updateById(scheduleForm))
            return R.success("更换成功");
        return R.err("更换失败");
    }


    /**
     * 获取合适的换班人员
     * @param scheduleId 班次id
     * @return 合适的人员信息
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @GetMapping("/suitableStaff")
    public R<List<StaffInfo>> getSuitableStaff(@RequestParam Integer scheduleId){
        ScheduleVo scheduleVo = scheduleVoService.getById(scheduleId);
        List<StaffInfo> staffInfoList = staffInfoService.getSuitableStaff(scheduleVo);
        return R.success(staffInfoList);
    }
}
