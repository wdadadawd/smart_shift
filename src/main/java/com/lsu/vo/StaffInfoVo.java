package com.lsu.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lsu.entity.StaffPreference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName staff_info_vo
 */
@TableName(value ="staff_info_vo")
@Data
public class StaffInfoVo implements Serializable {
    /**
     * 用户账号id，与user表关联
     */
    @TableId("user_id")
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
     * 门店名称
     */
    private String storeName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户个人偏好
     */
    @TableField(exist = false)
    private List<StaffPreference> staffPreferenceList;

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
     * 入职时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date entryTime;

    /**
     * 家庭住址
     */
    private String houseAddress;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}