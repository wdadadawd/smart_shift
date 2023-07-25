package com.lsu.filter;

import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zt
 * @create 2023-07-24 16:06
 */
public class ShiroUserFilter extends UserFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        CorsUtils.setHeader(request,response);
        if (((HttpServletRequest)request).getMethod().toUpperCase().equals("OPTIONS"))
            return true;
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
