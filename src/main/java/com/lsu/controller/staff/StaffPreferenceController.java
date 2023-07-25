package com.lsu.controller.staff;

import com.lsu.common.R;
import com.lsu.entity.StaffPreference;
import com.lsu.service.StaffPreferenceService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zt
 * @create 2023-03-18 21:00
 * 管理员工偏好
 */
@RestController
public class StaffPreferenceController {
    @Resource
    private StaffPreferenceService staffPreferenceService;

    /**
     * 根据员工id获取其全部偏好
     * @param staffId 员工id
     * @return 偏好集合
     */
    @RequiresRoles(value = {"normal","admin","shopowner"},logical = Logical.OR)
    @GetMapping("/preferenceList")
    public R<List<StaffPreference>> getPreferenceList(@RequestParam Integer staffId){
        List<StaffPreference> list = staffPreferenceService.getPreferenceListById(staffId);
        return R.success(list);
    }

    /**
     * 保存偏好值(修改员工偏好)
     * @param staffPreference 偏好信息
     * @return 保存结果
     */
    @RequiresRoles("normal")
    @PostMapping("/preference")
    public R<String> preservePreference(@RequestBody StaffPreference staffPreference){
        staffPreferenceService.updateOrInsertPreference(staffPreference);
        return R.success("保存成功");
    }

}
