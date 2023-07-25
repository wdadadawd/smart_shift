package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 账号唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号身份(管理员/普通用户)
     */
    private String status;

    public User(){}

    public User(String userName, String password, String status) {
        this.userName = userName;
        this.password = password;
        this.status = status;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}