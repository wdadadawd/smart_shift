package com.lsu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsu.vo.SignInFormVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【sign_in_form_vo】的数据库操作Service
* @createDate 2023-07-14 20:52:55
*/
public interface SignInFormVoService extends IService<SignInFormVo> {
    //搜索并分页获取签到信息
    Page<SignInFormVo> getPageSignInFormVo(Date date, Integer current, Integer size, String key, Integer storeId);

    //获取最新的未签到记录
    SignInFormVo getNewSignIn(Integer staffId);

    //根据id获取一条签到信息
    SignInFormVo getSignById(Long signId);

    //根据员工id获取员工的签到记录
    List<SignInFormVo> getSignListById(Integer staffId);

    //获取指定员工在指定日期内的签到记录
    List<SignInFormVo> getSignInFormVoByDate(Integer staffId,Date startDate,Date endDate);

    //统计今日到目前迟到班次
    Integer getNowLateCount(Integer storeId);

    //统计今日到目前缺勤班次
    Integer getNowDeficiencyCount(Integer storeId);

    //统计今日到目前请假班次
    Integer getNowLeaveCount(Integer storeId);

}
