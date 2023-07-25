package com.lsu.service;

import com.lsu.entity.RuleMap;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 30567
* @description 针对表【rule_map】的数据库操作Service
* @createDate 2023-07-09 15:21:00
*/
public interface RuleMapService extends IService<RuleMap> {
    //修改默认规则
    Integer updateRuleMap(RuleMap ruleMap);

    //添加默认规则
    Integer insertRuleMap(RuleMap ruleMap);

    //删除默认规则
    Integer deleteRuleMap(String type);

    //获取指定默认规则
    RuleMap getRuleMapByType(String type);
}
