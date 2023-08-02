package com.lsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsu.entity.StoreRule;

import java.util.List;


/**
* @author 30567
* @description 针对表【store_rule】的数据库操作Service
* @createDate 2023-03-12 14:59:29
*/
public interface StoreRuleService extends IService<StoreRule> {

    /**
     * 修改对应规则
     * @param storeRule 门店规则
     */
    Integer updateRule(StoreRule storeRule);

    /**
     * 删除对应规则
     * @param storeRule 门店规则
     */
    Integer deleteRule(StoreRule storeRule);

    /**
     * 添加对应规则
     * @param storeRule 门店规则
     */
    Integer insertRule(StoreRule storeRule);

    /**
     * 获取指定门店开启的规则的规则值
     */
    List<StoreRule> getStoreUseRule(Integer storeId);

    /**
     *  获取指定规则信息
     */
    StoreRule getStoreOneRule(Integer storeId,String type);
}
