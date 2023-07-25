package com.lsu.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author zt
 * @create 2023-07-24 14:08
 */
//@Component
//@WebFilter("/*")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CorsUtils.setHeader(servletRequest,servletResponse);
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
