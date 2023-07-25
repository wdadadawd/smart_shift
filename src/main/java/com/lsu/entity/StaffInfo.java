package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName staff_info
 */
@TableName(value ="staff_info")
@Data
public class StaffInfo implements Serializable {
    /**
     * 用户账号id，与user表关联
     */
    @TableId(type = IdType.INPUT)        //自输入
    private Integer userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 职位
     */
    private String position;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 所属门店
     */
    private Integer storeId;

    /**
     * 技能
     */
    private String skill;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String sex;

    /**
     * 家庭住址
     */
    private String houseAddress;

    /**
     * 入职时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date entryTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}