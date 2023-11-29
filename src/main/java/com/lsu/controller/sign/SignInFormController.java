package com.lsu.controller.sign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsu.common.R;
import com.lsu.config.ScheduleConfig;
import com.lsu.entity.SeizeOrders;
import com.lsu.entity.SignInForm;
import com.lsu.service.*;
import com.lsu.utils.DateUtils;
import com.lsu.vo.SignInFormVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.context.annotation.Role;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * @author zt
 * @create 2023-03-18 21:00
 * 管理签到
 */
@RestController
public class SignInFormController {
    @Resource
    private SignInFormService signInFormService;

    @Resource
    private StaffInfoService staffInfoService;

    @Resource
    private SignInFormVoService signInFormVoService;

    @Resource
    private ScheduleFormService scheduleFormService;

    @Resource
    private SeizeOrdersService seizeOrdersService;

    @Resource
    private ScheduleConfig scheduleConfig;

    /**
     * 分页获取门店签到日期(店长只会默认传回本门店的,不需传入门店id)
     * @param date 筛选日期,不传表示不筛选
     * @param current 页码
     * @param size 条数
     * @param key 搜索关键词
     * @param storeId 门店id,不传表示不按门店搜索
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","visitor"},logical = Logical.OR)
    @GetMapping("/signInFormList")
    public R<Page<SignInFormVo>> getSignInForm(@DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam(required = false) Date date,
                                               @RequestParam Integer current, @RequestParam Integer size, @RequestParam(defaultValue = "") String key
                                                , @RequestParam(required = false) Integer storeId, HttpSession session){
        //对请求做分析(店长发起的/管理员发起的)
        System.out.println(SecurityUtils.getSubject().hasRole("admin"));
        System.out.println(SecurityUtils.getSubject().hasRole("shopowner"));
        System.out.println(SecurityUtils.getSubject().hasRole("visitor"));
        String role = (String) session.getAttribute("role");
        if ("shopowner".equals(role)) {
            Integer userId = (Integer) session.getAttribute("userId");    //获取店长id
            storeId = staffInfoService.getStoreIdByUserId(userId);    //获取店长所在门店id
        }
        Page<SignInFormVo> pageSignInFormVo = signInFormVoService.getPageSignInFormVo(date, current, size, key, storeId);
        return R.success(pageSignInFormVo);
    }


    /**
     * 签到
     * @param signInForm (signId,way,seat)
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","normal"},logical = Logical.OR)
    @PutMapping("/signInForm")
    public R<String> updateSignInForm(@RequestBody SignInForm signInForm){
        //获取签到信息
        SignInFormVo signById = signInFormVoService.getSignById(signInForm.getSignId());
        //判断定位
        if (!signById.getStoreAddress().equals(signInForm.getSeat()))
            return R.err("位置错误,请重新定位");
        //判断签到时间
        Date date = new Date();
        Date startTime = signById.getStartTime();
        long timeDif = DateUtils.getTimeDif(date, startTime);
        System.out.println(timeDif);//
        int maxLagTime = 5;          //最大迟到时间
        if (timeDif > maxLagTime*60)                   //未到签到时间
            return R.err("未到签到时间");
        else if (timeDif >= -maxLagTime*60){           //签到时间内
            SeizeOrders seizeOrders = seizeOrdersService.getByScheduleId(signById.getStaffId(), signById.getScheduleId());
            if (seizeOrders!=null){           //如果是抢单班次
                seizeOrders.setIsAccomplish(true);    //更新抢单已完成
                seizeOrdersService.insertOrUpdateSeizeOrders(seizeOrders);
            }
            signInForm.setSignTime(date);
            signInForm.setStatus("已签到");
            signInFormService.updateSign(signInForm);
            return R.success("签到成功");
        }else{                                        //迟到
            signInForm.setSignTime(date);
            signInForm.setStatus("已迟到");
            signInFormService.updateSign(signInForm);
            return R.success("签到成功,迟到" + -timeDif + "分钟");
        }
    }


    /**
     * 获取今日最新的一条未签到信息
     * @param staffId 员工id
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","normal","visitor"},logical = Logical.OR)
    @GetMapping("/signInForm")
    public R<SignInFormVo> getSignInForm(@RequestParam Integer staffId){
        SignInFormVo newSignIn = signInFormVoService.getNewSignIn(staffId);
        return R.success(newSignIn);
    }


    /**
     * 获取指定员工全部签到记录
     * @param staffId 员工id
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","normal","visitor"},logical = Logical.OR)
    @GetMapping("/staffSignInList")
    public R<List<SignInFormVo>> getSignInListByStaffId(@RequestParam Integer staffId){
        List<SignInFormVo> signListById = signInFormVoService.getSignListById(staffId);
        return R.success(signListById);
    }


    /**
     * 统计指定门店今日到目前为止类情况的个数
     * @param type 统计类型
     * @param storeId 门店id
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","normal","visitor"},logical = Logical.OR)
    @GetMapping("/Statistics")
    public R<Integer> getStatistics (@RequestParam String type,@RequestParam Integer storeId){
        switch (type){
            case "expect":return R.success(scheduleFormService.getNowScheduleCount(storeId));                   //预期班次
            case "leave":return R.success(signInFormVoService.getNowLeaveCount(storeId));                    //请假班次
            case "late":return R.success(signInFormVoService.getNowLateCount(storeId));                     //迟到班次
            case "deficiency":return R.success(signInFormVoService.getNowDeficiencyCount(storeId));             //缺勤班次
            case "seize":return R.success(null);                    //抢单班次
            default:return R.err("类型错误");
        }
    }

    @RequiresRoles("admin")
    @PostMapping("/ScheduleConfig")
    public R<String> dailyPublishSign(){
        scheduleConfig.dailyPublishSign();
        return R.success("生成签到成功");
    }
}
