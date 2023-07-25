package com.lsu.controller.seize;

import com.lsu.common.R;
import com.lsu.entity.TopPremiums;
import com.lsu.service.TopPremiumsService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zt
 * @create 2023-07-21 20:41
 */
@RestController
public class TopPremiumsController {
    @Resource
    private TopPremiumsService topPremiumsService;

    /**
     * 获取指定的奖励设置
     * @param date 日期 (年、月)
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @GetMapping("/topPremiums")
    public R<List<TopPremiums>> getTopPremiums(@DateTimeFormat(pattern="yyyy-MM") @RequestParam Date date){
        List<TopPremiums> topPremiumsList = topPremiumsService.getTopPremiumsListByDate(date);
        return R.success(topPremiumsList);
    }


    /**
     * 保存奖励集合
     * @param topPremiumsList 设置奖励集合
     * @param date 设置的日期 (年、月)
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PostMapping("/topPremiums")
    public R<String> updateTopPremiums(@RequestBody List<TopPremiums> topPremiumsList,@DateTimeFormat(pattern="yyyy-MM") @RequestParam Date date){
        topPremiumsService.deleteTopPremiumsListByDate(date);
        topPremiumsService.insertTopPremiumsList(topPremiumsList,date);
        return R.success("保存成功");
    }

}
