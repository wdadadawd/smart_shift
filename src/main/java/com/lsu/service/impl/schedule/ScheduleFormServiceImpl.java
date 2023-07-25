package com.lsu.service.impl.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.ScheduleForm;
import com.lsu.mapper.schedule.ScheduleFormMapper;
import com.lsu.service.ScheduleFormService;
import com.lsu.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
* @author 30567
* @description 针对表【schedule_form】的数据库操作Service实现
* @createDate 2023-03-17 20:24:10
*/
@Service
public class ScheduleFormServiceImpl extends ServiceImpl<ScheduleFormMapper, ScheduleForm>
    implements ScheduleFormService{

    @Resource
    private ScheduleFormMapper scheduleFormMapper;

    /**
     * 删除指定日期集合的排班信息
     * @param dateList 日期集合
     * @param storeId 门店id
     * @return
     */
    @Override
    public Integer deleteWeekScheduleForm(List<Date> dateList,Integer storeId) {
        QueryWrapper<ScheduleForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId);
        queryWrapper.and(qw -> dateList.forEach(date -> qw.or().eq("date", date)));
        return scheduleFormMapper.delete(queryWrapper);
    }

    /**
     * 获取指定日期的排班信息
     * @param date 日期
     * @return
     */
    @Override
    public List<ScheduleForm> getDayScheduleForm(LocalDate date) {
        QueryWrapper<ScheduleForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date",date);
        return scheduleFormMapper.selectList(queryWrapper);
    }

    /**
     * 获取今日到目前为止的班次信息
     * @param storeId 门店id
     * @return
     */
    @Override
    public Integer getNowScheduleCount(Integer storeId) {
        QueryWrapper<ScheduleForm> queryWrapper = new QueryWrapper<>();
        //筛选门店id
        queryWrapper.eq("store_id",storeId);
        //筛选时间
        Date date = new Date();
        //班次开始时间 <= 现在时间
        queryWrapper.eq("date",DateUtils.getDate(date));
        queryWrapper.isNotNull("staff_id");
        queryWrapper.le("start_time", DateUtils.getTime(date));
        return Math.toIntExact(scheduleFormMapper.selectCount(queryWrapper));
    }

    /**
     * 获取指定日期内的班次
     * @param staffId 员工id
     * @param startDate 请假开始日期
     * @param endDate 请假结束日期
     * @return
     */
    @Override
    public List<ScheduleForm> getScheduleFormByDate(Integer staffId, Date startDate, Date endDate) {
        QueryWrapper<ScheduleForm> queryWrapper = new QueryWrapper<>();
        String date1 = DateUtils.getDate(startDate);
        String date2 = DateUtils.getDate(endDate);
        String time1 = DateUtils.getTime(startDate);
        String time2 = DateUtils.getTime(endDate);
        //只要不是班次结束时间 <= 请假开始时间,请假结束时间 <= 班次开始时间的签到都于该请假有关
        queryWrapper.not(q -> q.gt("date",date1).or(q1 -> q1.eq("date",date1).le("end_time",time1))
                .or().lt("date",date2).or(q1 -> q1.eq("date",date2).ge("start_time",time2)));
        queryWrapper.eq("staff_id",staffId);
        return scheduleFormMapper.selectList(queryWrapper);
    }
}




