package com.lsu.service.impl.seize;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.SeizeOrders;
import com.lsu.service.SeizeOrdersService;
import com.lsu.mapper.seize.SeizeOrdersMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 30567
* @description 针对表【seize_orders】的数据库操作Service实现
* @createDate 2023-07-18 19:38:32
*/
@Service
public class SeizeOrdersServiceImpl extends ServiceImpl<SeizeOrdersMapper, SeizeOrders>
    implements SeizeOrdersService{

    @Resource
    private SeizeOrdersMapper seizeOrdersMapper;

    /**
     * //添加或更新一个抢单
     * @param seizeOrders  抢单信息
     * @return
     */
    @Override
    public Boolean insertOrUpdateSeizeOrders(SeizeOrders seizeOrders) {
        QueryWrapper<SeizeOrders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schedule_id",seizeOrders.getScheduleId());
        return this.saveOrUpdate(seizeOrders,queryWrapper);
    }


    /**
     * 更新一个职员为空的抢单
     * @param seizeOrders 抢单
     * @return
     */
    @Override
    public Integer updateByIdAndByStaffId(SeizeOrders seizeOrders) {
        QueryWrapper<SeizeOrders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seize_id",seizeOrders.getSeizeId());
        queryWrapper.isNull("staff_id");
        return seizeOrdersMapper.update(seizeOrders,queryWrapper);
    }

    @Override
    public SeizeOrders getByScheduleId(Integer staffId,Long scheduleId) {
        QueryWrapper<SeizeOrders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("staff_id",staffId);
        queryWrapper.eq("schedule_id",scheduleId);
        return seizeOrdersMapper.selectOne(queryWrapper);
    }
}




