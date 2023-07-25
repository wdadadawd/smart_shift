package com.lsu.service;

import com.lsu.entity.TopPremiums;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【top_premiums】的数据库操作Service
* @createDate 2023-07-21 20:37:39
*/
public interface TopPremiumsService extends IService<TopPremiums> {
    //获取某月排行奖励
    List<TopPremiums> getTopPremiumsListByDate(Date date);

    //删除某月的排行奖励
    void deleteTopPremiumsListByDate(Date date);

    //保存某月的排行奖励的集合
    void insertTopPremiumsList(List<TopPremiums> list,Date date);
}
