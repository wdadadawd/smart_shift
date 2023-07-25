package com.lsu.controller;

import com.lsu.common.R;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zt
 * @create 2023-02-16 20:32
 * 捕捉异常处理
 */
@ControllerAdvice
@ResponseBody
public class PermissionsException {

    @ExceptionHandler(UnauthorizedException.class)     //抓取的异常信息
    public R<String> unauthorizedException(Exception ex){
        return R.err("无权限");
    }


    @ExceptionHandler(AuthorizationException.class)
    public R<String> authorizationException(Exception ex){
        return R.err("权限认证失败");
    }

//    @ExceptionHandler(NullPointerException.class)
//    public R<String> NullPointerException(){
//        return R.err("系统错误");
//    }
}