package com.lsu.controller.staff;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsu.common.R;
import com.lsu.entity.StaffInfo;
import com.lsu.service.StaffInfoService;
import com.lsu.service.StaffInfoVoService;
import com.lsu.service.StaffPreferenceService;
import com.lsu.vo.StaffInfoVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zt
 * @create 2023-03-18 19:27
 * 管理职员个人信息
 */
@RestController
public class StaffInfoController {
    @Resource
    private StaffInfoService staffInfoService;

    @Resource
    private StaffPreferenceService staffPreferenceService;

    @Resource
    private StaffInfoVoService staffInfoVoService;

    /**
     * 添加用户信息
     * @param staffInfo 用户信息 (userId,name,position,phone,email,storeId,skill,age,sex)
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PostMapping("/staffInfo")
    public R<String> insertStaffInfo(@RequestBody StaffInfo staffInfo){
        if (staffInfoService.insertStaffInfo(staffInfo)>0)
            return R.success("添加成功");
        return R.err("添加失败");
    }


    /**
     * 修改用户信息
     * @param staffInfo 用户信息 (userId,name,position,phone,email,storeId,skill,age,sex)
     * @return
     */
    @RequiresRoles(value = {"admin","normal","shopowner"},logical = Logical.OR)
    @PutMapping("/staffInfo")
    public R<String> updateStaffInfo(@RequestBody StaffInfo staffInfo){
        if (staffInfoService.updateStaffInfo(staffInfo)>0)
            return R.success("修改成功");
        return R.err("修改失败");
    }

    /**
     * 根据账号Id获取用户信息
     * @param userId 账号id
     * @return
     */
    @RequiresRoles(value = {"admin","normal","shopowner","visitor"},logical = Logical.OR)
    @GetMapping("/staffInfo")
    public R<StaffInfoVo> getStaffInfo(@RequestParam Integer userId){
        StaffInfoVo staffInfoVo = staffInfoVoService.getById(userId);
        staffInfoVo.setStaffPreferenceList(staffPreferenceService.getPreferenceListById(userId));
        return R.success(staffInfoVo);
    }

    /**
     * 搜索员工信息
     * @param current 页码
     * @param size 条数
     * @param key 关键字
     * @return 对应的分页信息
     */
    @RequiresRoles(value = {"admin","shopowner","visitor"},logical = Logical.OR)
    @GetMapping("/staffInfoList")
    public R<Page<StaffInfoVo>> getStaffInfoList(HttpSession session, @RequestParam Integer current,
                             @RequestParam Integer size, @RequestParam(defaultValue = "") String key,@RequestParam(required = false) boolean sort){
        Page<StaffInfoVo> page;
        //对请求做分析(店长发起的/管理员发起的)
        String role = (String) session.getAttribute("role");
        if ("shopowner".equals(role)) {
            Integer userId = (Integer) session.getAttribute("userId");    //获取店长id
            Integer storeId = staffInfoService.getStoreIdByUserId(userId);    //获取店长所在门店id
            page = staffInfoVoService.selectStaffInfoVo(current, size, key,storeId,sort);
        } else {
            page = staffInfoVoService.selectStaffInfoVo(current, size, key,null,sort);
        }
        //获取分页中的记录并处理
        List<StaffInfoVo> records = page.getRecords();
        if (records == null)
            return R.success(page);
        for(int i=0;i<records.size();i++){          //遍历员工信息，为员工偏好信息赋值
            StaffInfoVo staffInfoVo = records.get(i);
            staffInfoVo.setStaffPreferenceList(staffPreferenceService.getPreferenceListById(staffInfoVo.getUserId()));
            records.set(i,staffInfoVo);
        }
        page.setRecords(records);
        return R.success(page);
    }
}
