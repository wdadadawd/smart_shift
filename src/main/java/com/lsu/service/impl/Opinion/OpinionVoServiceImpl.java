package com.lsu.service.impl.Opinion;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.vo.OpinionVo;
import com.lsu.service.OpinionVoService;
import com.lsu.mapper.opinion.OpinionVoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 30567
* @description 针对表【opinion_vo】的数据库操作Service实现
* @createDate 2023-08-02 22:47:37
*/
@Service
public class OpinionVoServiceImpl extends ServiceImpl<OpinionVoMapper, OpinionVo>
    implements OpinionVoService{

    @Resource
    private OpinionVoMapper opinionVoMapper;


    /**
     * 根据门店id获取所有门店评价
     * @param storeId 门店id
     * @return
     */
    @Override
    public List<OpinionVo> getOpinionListByStoreId(Integer storeId) {
        QueryWrapper<OpinionVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId);
        return opinionVoMapper.selectList(queryWrapper);
    }
}




