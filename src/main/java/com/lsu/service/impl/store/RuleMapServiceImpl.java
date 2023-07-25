package com.lsu.service.impl.store;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.RuleMap;
import com.lsu.service.RuleMapService;
import com.lsu.mapper.store.RuleMapMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 30567
* @description 针对表【rule_map】的数据库操作Service实现
* @createDate 2023-07-09 15:21:00
*/
@Service
public class RuleMapServiceImpl extends ServiceImpl<RuleMapMapper, RuleMap>
    implements RuleMapService{

    @Resource
    private RuleMapMapper ruleMapMapper;

    /**
     * 根据规则类型更新指定规则
     * @param ruleMap 规则信息
     * @return
     */
    @Override
    public Integer updateRuleMap(RuleMap ruleMap) {
        QueryWrapper<RuleMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rule_type",ruleMap.getRuleType());
        return ruleMapMapper.update(ruleMap,queryWrapper);
    }

    /**
     * 添加规则
     * @param ruleMap 规则信息
     * @return
     */
    @Override
    public Integer insertRuleMap(RuleMap ruleMap) {
        List<RuleMap> list = this.list();
        list.sort((o1, o2) -> {
            String type1 = o1.getRuleType();
            String type2 = o2.getRuleType();
            if (type1.length()!=type2.length())
                return -type1.length()+type2.length();
            return -type1.substring(type1.length()-1).compareTo(type2.substring(type2.length()-1));
        });  //排序
        String type = list.get(0).getRuleType();                   //获取最大类型的类型
        char lastChar = type.charAt(type.length()-1);          //最后一个字母
        if (lastChar == 'z')                            //获取下一个类型
            type += 'a';
        else
            type = type.substring(0,type.length()-1) + (char)(lastChar+1);
        ruleMap.setRuleType(type);                  //设置规则类型
        return ruleMapMapper.insert(ruleMap);
    }

    /**
     * 删除指定类型的默认规则
     * @param type 规则类型
     * @return
     */
    @Override
    public Integer deleteRuleMap(String type) {
        QueryWrapper<RuleMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rule_type",type);
        return ruleMapMapper.delete(queryWrapper);
    }

    /**
     * 获取指定类型的默认规则信息
     * @param type 规则类型
     * @return
     */
    @Override
    public RuleMap getRuleMapByType(String type) {
        QueryWrapper<RuleMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rule_type",type);
        return  ruleMapMapper.selectOne(queryWrapper);
    }
}




