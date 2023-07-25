package com.lsu.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zt
 * @create 2023-07-24 14:28
 */
public class ShiroAuthcFilter extends FormAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        CorsUtils.setHeader(request,response);
        if (((HttpServletRequest)request).getMethod().equalsIgnoreCase("OPTIONS"))
            return true;
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
