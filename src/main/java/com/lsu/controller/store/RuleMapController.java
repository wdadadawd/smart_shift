package com.lsu.controller.store;

import com.lsu.common.R;
import com.lsu.entity.RuleMap;
import com.lsu.service.RuleMapService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zt
 * @create 2023-07-09 20:24
 */
@RestController
public class RuleMapController {

    @Resource
    private RuleMapService ruleMapService;

    /**
     *
     * @param ruleMap (ruleDetail,ruleName)
     * @return 添加结果
     */
    @RequiresRoles("admin")
    @PostMapping("/ruleMap")
    public R<String> insertRuleMap(@RequestBody RuleMap ruleMap){
        if (ruleMapService.insertRuleMap(ruleMap)>0)
            return R.success("添加成功");
        return R.err("添加失败");
    }

    /**
     * 根据类型删除对应的默认规则
     * @param type 规则类型
     * @return 删除结果
     */
    @RequiresRoles("admin")
    @DeleteMapping("/ruleMap")
    public R<String> deleteRuleMap(@RequestParam String type){
        if (ruleMapService.deleteRuleMap(type)>0)
            return R.success("删除成功");
        return R.err("删除失败");
    }

    /**
     * 修改默认规则
     * @param ruleMap (ruleType,ruleDetail,ruleName)
     * @return 修改结果
     */
    @RequiresRoles("admin")
    @PutMapping("/ruleMap")
    public R<String> updateRuleMap(@RequestBody RuleMap ruleMap){
        Integer result = ruleMapService.updateRuleMap(ruleMap);
        if (result>0)
            return R.success("修改成功");
        return R.err("修改失败");
    }

    /**
     * 获取指定类型的规则
     * @param type 规则类型
     * @return 对应的规则信息
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @GetMapping("/ruleMap")
    public R<RuleMap> getRuleMap(@RequestParam String type){
        RuleMap ruleMap = ruleMapService.getRuleMapByType(type);
        return R.success(ruleMap);
    }

    /**
     * 获取全部默认规则
     * @return 全部默认规则集合
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @GetMapping("/ruleMapList")
    public R<List<RuleMap>> getRuleMapList(){
        List<RuleMap> list = ruleMapService.list();
        return R.success(list);
    }

}
