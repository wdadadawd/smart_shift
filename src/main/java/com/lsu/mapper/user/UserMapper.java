package com.lsu.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsu.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 30567
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-03-12 14:59:29
* @Entity generator.com.lsu.entity.User
*/
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT info FROM permissions WHERE role_name= #{roleName}")
    List<String> getPermissions(String roleName);
}




