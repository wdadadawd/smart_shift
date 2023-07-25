package com.lsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsu.entity.PassengerFlow;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【passenger_flow】的数据库操作Service
* @createDate 2023-03-12 14:59:29
*/
public interface PassengerFlowService extends IService<PassengerFlow> {
    /**
     * 根据门店id和日期获取当天该门店的客流量预测值
     * @param date 日期
     * @param storeId 门店id
     * @return 当前客流量预测值集合
     */
    List<PassengerFlow> getFlowByIdAndDate(@DateTimeFormat(pattern="yyyy-MM-dd") Date date, Integer storeId);

    /**
     * 保存或修改当天的客流量预测值
     * @param list
     */
    void insertOrUpdateFlow(List<PassengerFlow> list);

    /**
     * 获取指定门店指定日期的全部预测客流量
     * @param dateList 日期集合
     * @return
     */
    List<List<PassengerFlow>> getDayListPassengerFlowList(List<Date> dateList,Integer storeId);

    /**
     * 获取指定门店已有预测客流量的往后日期,升序
     * @return
     */
    List<Date> getReadyDay(Integer storeId);

    /**
     * 判断指定门店本周的预测客流量是否已经全部导入
     * @param dateList 日期集合
     * @return 判断结果
     */
    Boolean judgeDatePassengerFlow(List<Date> dateList,Integer storeId);
}
