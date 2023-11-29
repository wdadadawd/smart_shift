package com.lsu.controller.staff;

import com.lsu.common.R;
import com.lsu.entity.SkillMap;
import com.lsu.service.SkillMapService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zt
 * @create 2023-07-10 19:43
 */
@RestController
public class SkillMapController {
    @Resource
    private SkillMapService skillMapService;

    /**
     * 添加技能
     * @param skillMap (skillName,skillIntroduction)
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PostMapping("/skillMap")
    public R<String> insertSkillMap(@RequestBody SkillMap skillMap){
        if (skillMapService.save(skillMap))
            return R.success("添加成功");
        return R.err("添加失败");
    }

    /**
     * 删除技能
     * @param skillId 技能id
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @DeleteMapping("/skillMap")
    public R<String> deleteSkillMap(@RequestParam Integer skillId){
        if (skillMapService.removeById(skillId))
            return R.success("删除成功");
        return R.err("删除失败");
    }

    /**
     * 修改技能信息
     * @param skillMap (skillId,skillName,skillIntroduction)
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PutMapping("/skillMap")
    public R<String> updateSkillMap(@RequestBody SkillMap skillMap){
        if (skillMapService.updateById(skillMap))
            return R.success("修改成功");
        return R.err("修改失败");
    }

    /**
     * 获取全部技能信息
     * @return 全部技能信息
     */
    @RequiresRoles(value = {"admin","shopowner","visitor"},logical = Logical.OR)
    @GetMapping("/skillMapList")
    public R<List<SkillMap>> getSkillList(){
        return R.success(skillMapService.list());
    }


}
