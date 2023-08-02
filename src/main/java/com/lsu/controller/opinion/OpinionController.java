package com.lsu.controller.opinion;

import com.lsu.common.R;
import com.lsu.entity.Opinion;
import com.lsu.service.OpinionService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zt
 * @create 2023-07-31 18:42
 */
@RestController
public class OpinionController {
    @Resource
    private OpinionService opinionService;

    /**
     * 发表一条意见
     * @param opinion (storeId,proposalTime,opinionContent) createTime,likeCount
     * @return
     */
    @RequiresRoles(value = {"client","normal"},logical = Logical.OR)
    @PostMapping("/opinion")
    public R<String> insertOpinion(@RequestBody Opinion opinion){
        opinion.setLikeCount(0);                //设置初始点赞数
        opinion.setCreateTime(new Date());      //设置创建时间
        if (opinionService.save(opinion))
            return R.success("发表成功");
        return R.err("发表失败");
    }





}
