package com.lsu.service.impl.store;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.StoreRule;
import com.lsu.mapper.store.StoreRuleMapper;
import com.lsu.service.StoreRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【store_rule】的数据库操作Service实现
* @createDate 2023-03-12 14:59:29
*/
@Service
@Transactional
public class StoreRuleServiceImpl extends ServiceImpl<StoreRuleMapper, StoreRule>
    implements StoreRuleService {
    @Resource
    private StoreRuleMapper storeRuleMapper;

    /**
     * 修改对应规则
     * @param storeRule 门店规则
     */
    @Override
    public Integer updateRule(StoreRule storeRule) {
        QueryWrapper<StoreRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeRule.getStoreId()).eq("type",storeRule.getType());
        storeRule.setUpdateTime(new Date());          //设置更新时间
        return storeRuleMapper.update(storeRule,queryWrapper);
    }

    /**
     * 删除对应规则
     * @param storeRule 门店规则
     */
    @Override
    public Integer deleteRule(StoreRule storeRule) {
        QueryWrapper<StoreRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeRule.getStoreId()).eq("type",storeRule.getType());
        return storeRuleMapper.delete(queryWrapper);
    }

    /**
     * 添加对应规则
     * @param storeRule 门店规则
     */
    @Override
    public Integer insertRule(StoreRule storeRule) {
        storeRule.setUpdateTime(new Date());            //设置更新时间
        storeRule.setStatus(true);                     //设置状态
        return storeRuleMapper.insert(storeRule);
    }

    /**
     * 获取指定门店开启的规则的规则值
     * @param storeId 门店id
     * @return
     */
    @Override
    public List<StoreRule> getStoreUseRule(Integer storeId) {
        QueryWrapper<StoreRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("type","value");
        queryWrapper.eq("status",true).eq("store_id",storeId);
        return storeRuleMapper.selectList(queryWrapper);
    }

    /**
     * 获取指定规则信息
     * @param storeId 门店id
     * @param type 规则类型
     * @return
     */
    @Override
    public StoreRule getStoreOneRule(Integer storeId, String type) {
        QueryWrapper<StoreRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId).eq("type",type);
        return storeRuleMapper.selectOne(queryWrapper);
    }

}




