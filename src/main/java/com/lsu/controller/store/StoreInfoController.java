package com.lsu.controller.store;

import com.lsu.common.R;
import com.lsu.vo.RuleVo;
import com.lsu.entity.StoreInfo;
import com.lsu.service.RuleVoService;
import com.lsu.service.StaffInfoService;
import com.lsu.service.StoreInfoService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zt
 * @create 2023-03-17 20:53
 * 管理门店信息
 */
@RestController
public class  StoreInfoController {
    @Resource
    private StoreInfoService storeInfoService;

    @Resource
    private StaffInfoService staffInfoService;

    @Resource
    private RuleVoService ruleVoService;


    /**
     * 获取所有门店信息(含规则)
     * @return 对应的分页信息
     * 管理员
     */
    @RequiresRoles(value = {"admin","visitor"},logical = Logical.OR)
    @GetMapping("/storeInfoList")
    public R<List<StoreInfo>> getAllStoreInfoAndRules(){
        List<StoreInfo> storeInfoList = storeInfoService.getAllStoreInfo(); //获取
        if (storeInfoList == null)
            return R.success(null);
        for (int i=0;i<storeInfoList.size();i++){               //遍历每个门店
            StoreInfo storeInfo = storeInfoList.get(i);
            List<RuleVo> rules = ruleVoService.getRuleListByStoreId(storeInfo.getId());//获取该门店所有规则
            storeInfo.setStoreRules(rules);            //设置规则属性
            storeInfo.setStaffSum(staffInfoService.getStaffSumByStoreId(storeInfo.getId()));//设置门店人数
            storeInfoList.set(i,storeInfo);
        }
        return R.success(storeInfoList);
    }

    /**
     * 获取指定门店的门店信息
     * @param storeId 门店id
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","visitor"},logical = Logical.OR)
    @GetMapping("/storeInfo")
    public R<StoreInfo> getStoreInfoByUserId(@RequestParam Integer storeId){
        StoreInfo storeInfo = storeInfoService.getById(storeId);        //获取门店信息
        storeInfo.setStoreRules(ruleVoService.getRuleListByStoreId(storeId));  //填入门店规则信息
        storeInfo.setStaffSum(staffInfoService.getStaffSumByStoreId(storeInfo.getId()));//设置门店人数
        return R.success(storeInfo);
    }

    /**
     * 添加门店信息并创建所有类型规则(默认禁用)
     * @param storeInfo 门店信息
     * @param userId 创建者id
     * @return 添加结果
     * 管理员
     */
    @RequiresRoles("admin")
    @PostMapping("/storeInfo")
    public R<String> insertStore(@RequestBody StoreInfo storeInfo,@RequestParam Integer userId){
        if (storeInfoService.insertStoreInfo(storeInfo,userId)>0)          //添加门店信息
            return R.success("添加成功");
        return R.err("添加失败");
    }

    /**
     * 删除门店信息及门店规则
     * @param storeId 门店id
     * @return 删除结果
     * 管理员
     */
    @RequiresRoles("admin")
    @DeleteMapping("/storeInfo")
    public R<String> deleteStore(@RequestParam Integer storeId){
        if (storeInfoService.deleteStoreInfo(storeId) > 0)     //删除门店信息
            return R.success("删除成功");
        return R.err("删除失败");
    }

    /**
     * 修改门店信息
     * @param storeInfo 门店信息
     * @return 修改结果
     * 店长本店、管理员
     */
    @RequiresRoles(value = {"shopowner","admin"},logical = Logical.OR)
    @PutMapping("/storeInfo")
    public R<String> updateStore(@RequestBody StoreInfo storeInfo){
        if (storeInfoService.updateStoreInfo(storeInfo)>0)
            return R.success("修改成功");
        return R.err("修改失败");
    }

    /**
     * 获取可为员工分配的门店信息
     * @return 门店名称和id
     */
    @RequiresRoles(value = {"shopowner","admin","visitor"},logical = Logical.OR)
    @GetMapping("/storeName")
    public R<List<StoreInfo>> getStoreName(HttpSession session){
        List<StoreInfo> list = new ArrayList<StoreInfo>();
        String role = (String)session.getAttribute("role");
        if ("shopowner".equals(role)){                         //如果是店长身份
            Integer userId = (Integer)session.getAttribute("userId");
            list.add(storeInfoService.getOneStore(staffInfoService.getStoreIdByUserId(userId)));
        }else{
            list = storeInfoService.getALLStoreName();
        }
        return R.success(list);
    }
}
