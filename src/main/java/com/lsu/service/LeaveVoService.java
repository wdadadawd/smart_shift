package com.lsu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsu.vo.LeaveVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【leave_vo】的数据库操作Service
* @createDate 2023-07-16 13:02:21
*/
public interface LeaveVoService extends IService<LeaveVo> {

    //搜索分页获取签到信息
    Page<LeaveVo> getLeaveVoPage(Integer current, Integer size, String key, Date startDate, Date endDate,Integer storeId);

    //获取用户发起的请求申请
    List<LeaveVo> getLeaveVoByUserId(Integer userId);


}
