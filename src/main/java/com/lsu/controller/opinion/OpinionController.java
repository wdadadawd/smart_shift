package com.lsu.controller.opinion;

import com.lsu.common.R;
import com.lsu.entity.Opinion;
import com.lsu.service.OpinionService;
import com.lsu.service.OpinionVoService;
import com.lsu.vo.OpinionVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zt
 * @create 2023-07-31 18:42
 */
@RestController
public class OpinionController {
    @Resource
    private OpinionService opinionService;

    @Resource
    private OpinionVoService opinionVoService;

    /**
     * 发表一条意见
     * @param opinion (storeId,proposalId,proposalTime,opinionContent,type) createTime,likeCount
     * @return
     */
    @RequiresRoles(value = {"client","normal"},logical = Logical.OR)
    @PostMapping("/opinion")
    public R<String> insertOpinion(@RequestBody Opinion opinion){
        System.out.println(opinion);
        opinion.setLikeCount(0);                //设置初始点赞数
        opinion.setCreateTime(new Date());      //设置创建时间
        if (opinionService.save(opinion))
            return R.success("发表成功");
        return R.err("发表失败");
    }

    /**
     * 获取全部意见
     * @param storeId 门店id
     * @return
     */
    @RequiresRoles(value = {"client","normal","visitor"},logical = Logical.OR)
    @GetMapping ("/opinion")
    public R<List<OpinionVo>> getAllOpinion(@RequestParam Integer storeId){
        List<OpinionVo> listByStoreId = opinionVoService.getOpinionListByStoreId(storeId);
        return R.success(listByStoreId);
    }


}
