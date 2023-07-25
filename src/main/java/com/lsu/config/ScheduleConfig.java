package com.lsu.config;

import com.lsu.entity.ScheduleForm;
import com.lsu.entity.SignInForm;
import com.lsu.service.LeaveFormService;
import com.lsu.service.ScheduleFormService;
import com.lsu.service.SignInFormService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author zt
 * @create 2023-07-14 13:57
 * 定时任务配置类
 */
@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Resource
    private ScheduleFormService scheduleFormService;        //班次信息事务

    @Resource
    private SignInFormService signInFormService;             //签到事务

    @Resource
    private LeaveFormService leaveFormService;               //请假事务

    //自动发布签到
    @Scheduled(cron = "30 50 14 * * ?")              //每日2点自动发布当天的签到
    public void dailyPublishSign(){
        //1.获取今日班次信息
        List<ScheduleForm> scheduleFormList = scheduleFormService.getDayScheduleForm(LocalDate.now());
        if (scheduleFormList==null||scheduleFormList.size() == 0)
            return;
        //2.遍历班次信息,发布签到
        for (ScheduleForm s:scheduleFormList){
            SignInForm signInForm = new SignInForm();
            if (s.getStaffId()==null)                   //不发布开放班次的
                continue;
                signInForm.setStatus("未签到");
            //设置签到对应的班次信息
            signInForm.setScheduleId(s.getScheduleId());
            //设置员工id
            signInForm.setStaffId(s.getStaffId());
            signInFormService.insertOrUpdateSign(signInForm);   //如果已经存在对应(班次id和员工id相同)的签到信息,则更新(不做改动)
        }
    }
}
