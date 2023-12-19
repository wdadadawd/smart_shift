package com.lsu.config;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author zt
 * @create 2023-07-24 20:58
 */
public class MyShiroSessionManager extends DefaultWebSessionManager {

    public final static String HEADER_TOKEN_NAME = "JSessionId";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    /**
     * 逻辑:
     *     如果请求头中有 JSessionId，就分析它；
     *     没有就调用父类的方法
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response){
        String JSessionId = request.getParameter(HEADER_TOKEN_NAME);
        if(JSessionId == null || "".equals(JSessionId)) {
            return super.getSessionId(request, response);
        } else {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, JSessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return JSessionId;
        }
    }
}
