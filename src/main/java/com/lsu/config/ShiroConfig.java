package com.lsu.config;

import com.lsu.filter.ShiroAnonFilter;
import com.lsu.filter.ShiroAuthcFilter;
import com.lsu.filter.ShiroUserFilter;
import com.lsu.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zt
 * @create 2023-01-21 0:57
 */
@Configuration
public class ShiroConfig {
    @Resource
    private MyRealm myRealm;


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());

        //设置过滤器
        Map<String, Filter> map = new HashMap<>();
        map.put("anon-i",new ShiroAnonFilter());
        map.put("authc-i",new ShiroAuthcFilter());
        map.put("user-i",new ShiroUserFilter());
        shiroFilterFactoryBean.setFilters(map);

        //设置拦截范围
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());

        //设置登录地址
        shiroFilterFactoryBean.setLoginUrl("/index.html");
        return shiroFilterFactoryBean;
    }


    //配置 SecurityManager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        //1 创建 defaultWebSecurityManager 对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //2 创建加密对象，并设置相关属性
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //2.1 采用 md5 加密
        matcher.setHashAlgorithmName("md5");
        //2.2 迭代加密次数
        matcher.setHashIterations(3);
        //3 将加密对象存储到 myRealm 中
        myRealm.setCredentialsMatcher(matcher);
        //4 将 myRealm 存入 defaultWebSecurityManager 对象
        defaultWebSecurityManager.setRealm(myRealm);
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());     //rememberMe
//        defaultWebSecurityManager.setSessionManager(sessionManager());
        //5 返回
        return defaultWebSecurityManager;
    }

//    @Bean
//    public MyShiroSessionManager sessionManager() {
//        return new MyShiroSessionManager();
//    }

    //配置 Shiro 内置过滤器拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition
    shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
//        definition.addPathDefinition("/*", "anon");
        //设置不认证可以访问的资源
        definition.addPathDefinition("/login", "anon-i");
        definition.addPathDefinition("/test","anon-i");
        definition.addPathDefinition("/login.html", "anon-i");
        definition.addPathDefinition("/sendEmailCode", "anon-i");
        //配置登出过滤器
        definition.addPathDefinition("/logout","logout");
        //设置需要进行登录认证的拦截范围
        definition.addPathDefinition("/**", "authc-i");
        //添加存在用户的过滤器（rememberMe）
        definition.addPathDefinition("/**","user-i");
        return definition;
    }

    public SimpleCookie rememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        //设置跨域
        //cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30*24*60*60);
        return cookie;
    }

    //创建 Shiro 的 cookie 管理对象
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey("1234567890987654".getBytes());
        return cookieRememberMeManager;
    }
}
