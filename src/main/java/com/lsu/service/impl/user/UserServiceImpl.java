package com.lsu.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.StaffPreference;
import com.lsu.entity.User;
import com.lsu.mapper.staff.StaffInfoMapper;
import com.lsu.mapper.staff.StaffPreferenceMapper;
import com.lsu.mapper.user.UserMapper;
import com.lsu.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 30567
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-03-12 14:59:29
*/
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    @Resource
    private StaffInfoMapper staffInfoMapper;

    @Resource
    private StaffPreferenceMapper staffPreferenceMapper;


    /**
     *
     * 根据用户名查找用户账号信息
     *
     * @param userName   用户名
     * @return    该用户名的用户账号信息
     */
    @Override
    public User getUserInfoByName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 添加一条账号信息
     * @param user 用户类
     */
    @Override
    public Integer insertUser(User user) {
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),"salt",3);
        user.setPassword(md5Hash.toHex());
        return userMapper.insert(user);
    }

    /**
     * 获取用户身份信息
     * @param userName 用户名
     * @return  用户身份信息
     */
    @Override
    public String getStatusByName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("status").eq("user_name",userName);
        User user = userMapper.selectOne(queryWrapper);
        if (user==null)
            return null;
        return user.getStatus();
    }

    /**
     * 注销账号
     * @param userId 账号id
     */
    @Override
    public Integer logoutUser(Integer userId) {
        QueryWrapper<StaffPreference> staffPreferenceQueryWrapper = new QueryWrapper<>();
        staffPreferenceQueryWrapper.eq("staff_id",userId);
        return userMapper.deleteById(userId);       //删除账号
    }

    /**
     * 修改密码
     * @param user 员工类
     */
    @Override
    public Integer updatePassword(User user) {
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),"salt",3);
        user.setPassword(md5Hash.toHex());
        return userMapper.updateById(user);
    }

    /**
     * 获取用户权限信息
     * @param roleName 角色名
     * @return 权限
     */
    @Override
    public List<String> getPermissions(String roleName) {
        return userMapper.getPermissions(roleName);
    }
}




