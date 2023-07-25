package com.lsu.service.impl.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.vo.ScheduleVo;
import com.lsu.service.ScheduleVoService;
import com.lsu.mapper.schedule.ScheduleVoMapper;
import com.lsu.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【schedule_vo】的数据库操作Service实现
* @createDate 2023-07-13 20:14:30
*/
@Service
public class ScheduleVoServiceImpl extends ServiceImpl<ScheduleVoMapper, ScheduleVo>
    implements ScheduleVoService{

    @Resource
    private ScheduleVoMapper scheduleVoMapper;

    /**
     * 获取指定日期指定门店的排班信息
     * @param storeId 门店id
     * @param date 日期
     * @return
     */
    @Override
    public List<ScheduleVo> getDayScheduleFormVo(Integer storeId, Date date) {
        QueryWrapper<ScheduleVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId);
        queryWrapper.eq("date",date);
        queryWrapper.orderByAsc("start_time");
        return scheduleVoMapper.selectList(queryWrapper);
    }

    /**
     * 获取指定日期开始的周指定门店的周排班信息
     * @param storeId 门店id
     * @param startDate 周日期开始时间
     * @return
     */
    @Override
    public List<List<ScheduleVo>> getWeekScheduleFormVo(Integer storeId, Date startDate) {
        List<List<ScheduleVo>> lists = new ArrayList<>();
        for (Date date : DateUtils.getWeekDay(startDate)) {
            lists.add(getDayScheduleFormVo(storeId,date));
        }
        return lists;
    }

    /**
     * 获取指定员工指定日期的班次
     * @param staffId 员工id
     * @param date 日期
     * @return
     */
    @Override
    public List<ScheduleVo> getStaffScheduleFormVo(Integer staffId, Date date) {
        QueryWrapper<ScheduleVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("staff_id",staffId);
        queryWrapper.eq("date",date);
        queryWrapper.orderByAsc("start_time");
        return scheduleVoMapper.selectList(queryWrapper);
    }
}




