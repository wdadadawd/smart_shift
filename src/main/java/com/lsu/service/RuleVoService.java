package com.lsu.service;

import com.lsu.vo.RuleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 30567
* @description 针对表【rule_vo】的数据库操作Service
* @createDate 2023-07-09 16:25:08
*/
public interface RuleVoService extends IService<RuleVo> {
    //根据门店id获取指定门店的全部规则
    List<RuleVo> getRuleListByStoreId(Integer storeId);
}
