package com.lsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsu.entity.ScheduleForm;

import javax.annotation.Resource;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【schedule_form】的数据库操作Service
* @createDate 2023-03-17 20:24:10
*/
public interface ScheduleFormService extends IService<ScheduleForm> {
    //删除指定门店集合指定日期的排班信息
    Integer deleteWeekScheduleForm(List<Date> dateList,Integer storeId);

    //获取指定日期的排班信息
    List<ScheduleForm> getDayScheduleForm(LocalDate date);

    //获取今日到目前为止的班次个数
    Integer getNowScheduleCount(Integer storeId);

    //获取指定日期内的班次
    List<ScheduleForm> getScheduleFormByDate(Integer staffId, Date startDate, Date endDate);
}
