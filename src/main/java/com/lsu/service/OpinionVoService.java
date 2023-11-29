package com.lsu.service;

import com.lsu.vo.OpinionVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 30567
* @description 针对表【opinion_vo】的数据库操作Service
* @createDate 2023-08-02 22:47:37
*/
public interface OpinionVoService extends IService<OpinionVo> {

    /**
     * 根据门店id获取所有门店评价
     * @param storeId 门店id
     * @return
     */
    List<OpinionVo> getOpinionListByStoreId(Integer storeId);
}
