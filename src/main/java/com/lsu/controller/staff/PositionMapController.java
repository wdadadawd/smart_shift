package com.lsu.controller.staff;

import com.lsu.common.R;
import com.lsu.entity.PositionMap;
import com.lsu.service.PositionMapService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zt
 * @create 2023-07-10 15:45
 */
@RestController
public class PositionMapController {

    @Resource
    private PositionMapService positionMapService;

    /**
     * 添加一个职位
     * @param positionMap (positionName,positionIntroduction)
     * @return 添加结果
     */
    @RequiresRoles("admin")
    @PostMapping("/positionMap")
    public R<String> insertPositionMap(@RequestBody PositionMap positionMap){
        if(positionMapService.save(positionMap))
            return R.success("添加成功");
        return R.err("添加失败");
    }

    /**
     * 删除职位
     * @param positionId 职位id
     * @return 删除结果
     */
    @RequiresRoles("admin")
    @DeleteMapping("/positionMap")
    public R<String> deletePositionMap(@RequestParam Integer positionId){
        if (positionMapService.removeById(positionId))
            return R.success("删除成功");
        return R.err("删除失败");
    }

    /**
     * 修改职位
     * @param positionMap (positionId,positionName,positionIntroduction)
     * @return
     */
    @RequiresRoles("admin")
    @PutMapping("/positionMap")
    public R<String> updatePositionMap(@RequestBody PositionMap positionMap){
        if (positionMapService.updateById(positionMap))
            return R.success("修改成功");
        return R.err("修改失败");
    }

    /**
     * 查询所有职位
     * @return 职位信息
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @GetMapping("/positionMapList")
    public R<List<PositionMap>> getPositionMap(HttpSession session){
        String role = (String) session.getAttribute("role");
        List<PositionMap> list = positionMapService.list();
        if ("shopowner".equals(role))                          //根据身份返回可选职位
            list.remove(new PositionMap("店长"));
        return R.success(list);
    }

}
