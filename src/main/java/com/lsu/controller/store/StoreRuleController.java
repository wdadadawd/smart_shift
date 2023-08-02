package com.lsu.controller.store;

import com.lsu.common.R;
import com.lsu.entity.RuleMap;
import com.lsu.service.RuleMapService;
import com.lsu.vo.RuleVo;
import com.lsu.entity.StoreRule;
import com.lsu.service.RuleVoService;
import com.lsu.service.StoreRuleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zt
 * @create 2023-03-14 22:13
 * 管理门店规则
 */
@RestController
public class StoreRuleController {

    @Resource
    private StoreRuleService storeRuleService;

    @Resource
    private RuleVoService ruleVoService;

    @Resource
    private  RuleMapService ruleMapService;

    /**
     * 添加门店规则
     * @param storeRule 规则类(storeId,type,value,userId)
     * @return 添加规则
     * 管理员、店长本店
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PostMapping("/storeRule")
    public R<String> addRule(@RequestBody StoreRule storeRule){
        if (storeRuleService.insertRule(storeRule)>0)
            return R.success("添加成功");
        else
            return R.err("添加失败");
    }

    /**
     * 删除门店规则
     * @param storeRule 规则类(storeId,type)
     * @return 删除信息
     * 管理员、店长本店
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @DeleteMapping("/storeRule")
    public R<String> deleteStoreRule(@RequestBody StoreRule storeRule){
        if (storeRuleService.deleteRule(storeRule)>0)
            return R.success("删除成功");
        else
            return R.err("删除失败");
    }

    /**
     * 修改规则
     * @param storeRule (storeId,type,value,userId)
     * @return 修改信息
     * 管理员、店长本店
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PutMapping("/storeRule")
    public R<String> updateStoreRule(@RequestBody StoreRule storeRule){
        //判断值是否为空
        String storeRuleValue = storeRule.getValue();    //规则值
//        RuleMap ruleMapByType = ruleMapService.getRuleMapByType(storeRule.getType());
//        String ruleDetail = ruleMapByType.getRuleDetail();   //规则细节
        if (storeRuleValue == null || storeRuleValue.isEmpty())
            return R.err("规则值为空");
        if (storeRuleService.updateRule(storeRule)>0)
            return R.success("修改成功");
        else
            return R.err("修改失败");
    }

    /**
     * 获取指定门店全部规则
     * @param storeId 门店id
     * @return 规则集合
     * 管理员、店长本店
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @GetMapping("/storeRule")
    public R<List<RuleVo>> getRuleByStoreId(@RequestParam Integer storeId){
        return R.success(ruleVoService.getRuleListByStoreId(storeId));
    }

}
