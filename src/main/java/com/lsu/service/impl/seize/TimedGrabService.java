package com.lsu.service.impl.seize;

import com.lsu.entity.ScheduleForm;
import com.lsu.entity.SeizeCache;
import com.lsu.entity.SeizeOrders;
import com.lsu.entity.SignInForm;
import com.lsu.service.ScheduleFormService;
import com.lsu.service.SeizeOrdersService;
import com.lsu.service.SignInFormService;
import com.lsu.utils.DateUtils;
import com.lsu.utils.RedisUtils;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zt
 * @create 2023-07-18 14:05
 */
@Service
public class TimedGrabService {

    private final TaskScheduler taskScheduler;

    @Resource
    private SignInFormService signInFormService;

    @Resource
    private SeizeOrdersService seizeOrdersService;

    @Resource
    private ScheduleFormService scheduleFormService;

    @Resource
    private RedisUtils redisUtils;

    public TimedGrabService() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(4);
        threadPoolTaskScheduler.initialize();
        this.taskScheduler = threadPoolTaskScheduler;

    }

    //创建定时抢单结束任务
    public void createLottery(Date endTime,Integer seizeId) {
        // 创建并调度定时任务
        taskScheduler.schedule(() -> {
            //获取所有抢了这单的人员
            List<SeizeCache> seizeCacheList = redisUtils.getSeizeCacheList(seizeId);
            redisUtils.deleteSeizeCacheList(seizeId);             //删除缓存
            if (seizeCacheList.size() == 0)             //无人抢单
                return;
            //......此处采用算法选取最合适的人选
            int i,max=0;
            for (i=0;i<seizeCacheList.size();i++) {
                SeizeCache seizeCache = seizeCacheList.get(i);
                double randomNumber = Math.random() * 100 + 0.001;
                randomNumber = Math.round(randomNumber * 100) / 100.0;
                seizeCache.setFitMeasure(randomNumber);
                seizeCacheList.set(i, seizeCache);
                if (randomNumber > seizeCacheList.get(max).getFitMeasure())
                    max = i;
            }
            SeizeCache seizeCache = seizeCacheList.get(max);
            SeizeOrders seizeOrders = new SeizeOrders(seizeId,"已被抢",seizeCache.getStaffId(),seizeCache.getTakeTime(), seizeCache.getFinalPremiums(), false);
            //更新抢单信息
            seizeOrdersService.updateByIdAndByStaffId(seizeOrders);
            //发布签到
            SeizeOrders seizeOrdersInfo = seizeOrdersService.getById(seizeId);
            SignInForm signInForm = new SignInForm(seizeOrders.getStaffId(),seizeOrdersInfo.getScheduleId(),"未签到");
            signInFormService.save(signInForm);    //保存
            //更新排班表
            ScheduleForm scheduleForm = new ScheduleForm();
            scheduleForm.setScheduleId(seizeOrdersInfo.getScheduleId());
            scheduleForm.setStaffId(seizeOrders.getStaffId());
            scheduleFormService.updateById(scheduleForm);
        },DateUtils.getOutSecondsTime(endTime,1));
    }
}
