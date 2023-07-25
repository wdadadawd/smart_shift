package com.lsu.vo;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @TableName sign_in_form_vo
 */
@TableName(value ="sign_in_form_vo")
@Data
public class SignInFormVo implements Serializable {
    /**
     * 签到id
     */
    private Long signId;

    /**
     * 对应的班次id
     */
    private Long scheduleId;

    /**
     * 签到时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date signTime;

    /**
     * 签到状态
     */
    private String status;

    /**
     * 签到方式
     */
    private String way;

    /**
     * 签到位置
     */
    private String seat;

    /**
     * 排班日期(年/月/日)
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;

    /**
     * 班次开始时间
     */
    @JsonFormat(pattern="HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="HH:mm:ss")
    private Date startTime;

    /**
     * 班次结束时间
     */
    @JsonFormat(pattern="HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="HH:mm:ss")
    private Date endTime;

    /**
     * 门店地址
     */
    private String storeAddress;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 姓名
     */
    private String staffName;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 门店id
     */
    private Integer storeId;

    /**
     * 员工id
     */
    private Integer staffId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}