package com.lsu.service.impl.passenger;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.PassengerFlow;
import com.lsu.mapper.passenger.PassengerFlowMapper;
import com.lsu.service.PassengerFlowService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【passenger_flow】的数据库操作Service实现
* @createDate 2023-03-12 14:59:29
*/
@Service
@Transactional
public class PassengerFlowServiceImpl extends ServiceImpl<PassengerFlowMapper, PassengerFlow>
    implements PassengerFlowService{

    @Resource
    private PassengerFlowMapper passengerFlowMapper;

    /**
     * 根据门店id和日期获取当天该门店的客流量预测值
     * @param date 日期
     * @param storeId 门店id
     * @return 当前客流量预测值集合
     */
    @Override
    public List<PassengerFlow> getFlowByIdAndDate(@DateTimeFormat(pattern="yyyy-MM-dd") Date date, Integer storeId) {
        //System.out.println(date);
        QueryWrapper<PassengerFlow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date",date).eq("store_id",storeId);
        queryWrapper.orderByAsc("start_time");               //按startTime排序
        List<PassengerFlow> list = this.list(queryWrapper);
        return list;
    }

    /**
     * 保存或修改当天的客流量预测值
     * @param list 客流量预测集合
     */
    @Override
    public void insertOrUpdateFlow(List<PassengerFlow> list) {
        for (PassengerFlow passengerFlow : list) {
            //System.out.println(passengerFlow);
            QueryWrapper<PassengerFlow> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("date", passengerFlow.getDate())
                    .eq("store_id", passengerFlow.getStoreId())
                    .eq("start_time", passengerFlow.getStartTime());
            if (passengerFlowMapper.selectOne(queryWrapper) == null) {
                passengerFlowMapper.insert(passengerFlow);
            } else {
                passengerFlowMapper.update(passengerFlow, queryWrapper);
            }
        }
    }


    /**
     * 获取日期集合中全部日期的预测客流量集合
     * @param dateList 日期集合
     * @return
     */
    @Override
    public List<List<PassengerFlow>> getDayListPassengerFlowList(List<Date> dateList,Integer storeId) {
        List<List<PassengerFlow>> lists = new ArrayList<>();
        for (int i=0;i<dateList.size();i++){
            QueryWrapper<PassengerFlow> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("date",dateList.get(i));
            queryWrapper.eq("store_id",storeId);
            queryWrapper.orderByAsc("start_time");            //按开始时间排序
            lists.add(passengerFlowMapper.selectList(queryWrapper));
        }
        return lists;
    }

    /**
     * 获取已有预测客流量的未来日期(今天以后的日期)
     * @return
     */
    @Override
    public List<Date> getReadyDay(Integer storeId) {
        QueryWrapper<PassengerFlow> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("date");
        queryWrapper.ge("date",new Date());             //日期比今天大
        queryWrapper.groupBy("date");                   //按日期分组
        queryWrapper.eq("store_id",storeId);            //根据门店id筛选
        queryWrapper.orderByAsc("date");                //根据日期升序
        List<Object> objects = passengerFlowMapper.selectObjs(queryWrapper);
        //将结果转换为日期集合
        List<Date> dateList = new ArrayList<>();
        for (Object obj : objects) {
            dateList.add((Date) obj);
        }
        return dateList;
    }

    /**
     * 判断指定门店本周的预测客流量是否已经全部导入
     * @param dateList 日期集合
     * @return
     */
    @Override
    public Boolean judgeDatePassengerFlow(List<Date> dateList,Integer storeId) {
        for (int i=0;i<dateList.size();i++){
            QueryWrapper<PassengerFlow> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("date",dateList.get(i));
            queryWrapper.eq("store_id",storeId);
            if (passengerFlowMapper.selectCount(queryWrapper) != 24)  //没有完整导入
                return false;
        }
        return true;
    }
}




