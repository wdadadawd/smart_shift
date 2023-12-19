package com.lsu.realm;

import com.lsu.entity.User;
import com.lsu.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zt
 * @create 2023-01-21 0:52
 */
@Component
public class MyRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;
    //自定义授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取用户的身份信息
        String userName = principalCollection.getPrimaryPrincipal().toString();
        //2.获取用户的角色信息(访问数据库)
        System.out.println("12312312313");
        String userRole = userService.getStatusByName(userName);
        System.out.println(userRole);
        //3.根据角色信息获取用户权限信息(访问数据库)
//        List<String> userPermission = userService.getPermissions(userRole);
        //4.创建对象，存储当前登录的用户的权限和角色
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(userRole);
//        info.addStringPermissions(userPermission);
        //5.返回
        return info;
    }


    //自定义登录认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1 获取用户身份信息
        String name = token.getPrincipal().toString();
        //2 调用业务层获取用户信息（数据库中）
        User user = userService.getUserInfoByName(name);
        //3 判断并将数据完成封装
        if(user!=null){
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    token.getPrincipal(),
                    user.getPassword(),
                    ByteSource.Util.bytes("salt"),
                    token.getPrincipal().toString()
            );
            return info;
        }
        return null;
    }
}
