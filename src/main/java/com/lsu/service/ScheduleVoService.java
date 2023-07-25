package com.lsu.service;

import com.lsu.vo.ScheduleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【schedule_vo】的数据库操作Service
* @createDate 2023-07-13 20:14:30
*/
public interface ScheduleVoService extends IService<ScheduleVo> {
    //获取指定日期和指定门店的排班表
    List<ScheduleVo> getDayScheduleFormVo(Integer storeId, Date date);

    //获取指定日期开始的和指定门店的一周排班表
    List<List<ScheduleVo>> getWeekScheduleFormVo(Integer storeId,Date startDate);

    //获取指定员工指定日期的班次信息
    List<ScheduleVo> getStaffScheduleFormVo(Integer staffId,Date date);
}
