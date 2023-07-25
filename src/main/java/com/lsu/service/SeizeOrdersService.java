package com.lsu.service;

import com.lsu.entity.SeizeOrders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 30567
* @description 针对表【seize_orders】的数据库操作Service
* @createDate 2023-07-18 19:38:32
*/
public interface SeizeOrdersService extends IService<SeizeOrders> {
    //添加或更新一个抢单
    Boolean insertOrUpdateSeizeOrders(SeizeOrders seizeOrders);

    //更新一个职员为空的抢单
    Integer updateByIdAndByStaffId(SeizeOrders seizeOrders);

    //根据职员id和班次id获取抢单
    SeizeOrders getByScheduleId(Integer staffId,Long scheduleId);

}
