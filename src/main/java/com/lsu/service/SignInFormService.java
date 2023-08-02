package com.lsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsu.entity.SignInForm;

/**
* @author 30567
* @description 针对表【sign_in_form】的数据库操作Service
* @createDate 2023-03-17 20:24:10
*/
public interface SignInFormService extends IService<SignInForm> {
    /**
     * 更新签到信息
     * @param signInForm 签到信息
     */
     Integer updateSign(SignInForm signInForm);

    /**
     * 添加或更新签到信息
     */
    Boolean insertOrUpdateSign(SignInForm signInForm);

    /**
     * 未存在则添加
     */
    void insertIfNull(SignInForm signInForm);
}
