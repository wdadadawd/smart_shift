package com.lsu.service.impl.store;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.vo.RuleVo;
import com.lsu.service.RuleVoService;
import com.lsu.mapper.store.RuleVoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 30567
* @description 针对表【rule_vo】的数据库操作Service实现
* @createDate 2023-07-09 16:25:08
*/
@Service
public class RuleVoServiceImpl extends ServiceImpl<RuleVoMapper, RuleVo>
    implements RuleVoService{

    @Resource
    private RuleVoMapper ruleVoMapper;

    /**
     * 根据门店id获取指定门店的全部规则
     * @return 指定门店的全部规则
     */
    @Override
    public List<RuleVo> getRuleListByStoreId(Integer storeId) {
        QueryWrapper<RuleVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId);
        return ruleVoMapper.selectList(queryWrapper);
    }
}




