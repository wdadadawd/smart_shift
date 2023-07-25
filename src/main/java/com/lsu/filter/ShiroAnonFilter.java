package com.lsu.filter;

import org.apache.shiro.web.filter.authc.AnonymousFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author zt
 * @create 2023-07-24 14:28
 */
public class ShiroAnonFilter extends AnonymousFilter {
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
        CorsUtils.setHeader(request,response);
        return true;
    }
}
