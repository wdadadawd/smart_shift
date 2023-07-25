package com.lsu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsu.entity.SeizeTopInfo;
import com.lsu.entity.StaffInfo;
import com.lsu.vo.SeizeVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 30567
* @description 针对表【seize_vo】的数据库操作Service
* @createDate 2023-07-20 18:33:32
*/
public interface SeizeVoService extends IService<SeizeVo> {
    //获取分页的抢单信息
    Page<SeizeVo> getSeizeVoPage(Integer current,Integer size,String key,Integer storeId);

    //获取员工合适的抢单信息
    List<SeizeVo> getSeizeList(StaffInfo staffInfo);

    //获取抢单排行榜
    List<SeizeTopInfo> getSeizeTopInfos(String date);
}
