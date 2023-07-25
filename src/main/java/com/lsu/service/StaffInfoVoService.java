package com.lsu.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsu.vo.StaffInfoVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
* @author 30567
* @description 针对表【staff_info_vo】的数据库操作Service
* @createDate 2023-03-27 21:34:41
*/
public interface StaffInfoVoService extends IService<StaffInfoVo> {
    //搜索获取员工列表
    Page<StaffInfoVo> selectStaffInfoVo(Integer current, Integer size, String key,Integer storeId,Boolean sort);
}
