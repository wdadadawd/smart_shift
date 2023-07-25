package com.lsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsu.entity.User;

import java.util.List;

/**
* @author 30567
* @description 针对表【user】的数据库操作Service
* @createDate 2023-03-12 14:59:29
*/
public interface UserService extends IService<User> {
    /**
     * 根据用户名查找用户账号信息
     * @param userName   用户名
     * @return    该用户名的用户账号信息
     */
    User getUserInfoByName(String userName);

    /**
     * 注册账号
     * @param user 用户类
     */
    Integer insertUser(User user);

    /**
     * 获取用户身份信息
     * @param userName 用户名
     * @return
     */
    String getStatusByName(String userName);

    /**
     * 注销账号
     * @param userId 账号id
     */
    Integer logoutUser(Integer userId);

    /**
     * 修改密码
     * @param user 员工类
     */
    Integer updatePassword(User user);

    /**
     * 获取用户权限信息
     * @param roleName 角色名
     * @return 权限
     */
    List<String> getPermissions(String roleName);
}
