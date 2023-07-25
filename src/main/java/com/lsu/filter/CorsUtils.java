package com.lsu.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zt
 * @create 2023-07-24 14:31
 */
public class CorsUtils {
    public static void setHeader(ServletRequest servletRequest, ServletResponse servletResponse){
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpServletRequest request = (HttpServletRequest)servletRequest;

        String origin = request.getHeader("Origin");

        if (origin == null){
            origin = request.getHeader("Referer");
        }
//        System.out.println(origin);
        // 设置允许跨域访问的域名、方法和请求头
        response.setHeader("Access-Control-Allow-Origin", origin); //允许访问的域名
        response.setHeader("Access-Control-Allow-Methods", "*"); //允许的请求方法
        response.setHeader("Access-Control-Allow-Credentials", "true");  //允许携带cookie
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Access-Control-Allow-Headers, X-Requested-With, Access-Control-Allow-Credentials");//允许的请求头
       // response.setHeader("Access-Control-Expose-Headers", "sessionid"); // 设置允许暴露的自定义响应头部字段
    }
}
