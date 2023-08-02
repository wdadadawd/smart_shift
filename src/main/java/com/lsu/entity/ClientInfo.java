package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName client_info
 */
@TableName(value ="client_info")
@Data
public class ClientInfo implements Serializable {
    /**
     * 用户账号id，与user表关联
     */
    @TableId
    private Integer clientId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String sex;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public ClientInfo() {
    }

    public ClientInfo(Integer clientId, String name, String email, Date createTime) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.createTime = createTime;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}