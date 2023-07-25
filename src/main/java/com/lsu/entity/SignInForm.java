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
 * @TableName sign_in_form
 */
@TableName(value ="sign_in_form")
@Data
public class SignInForm implements Serializable {

    /**
     * 签到id
     */
    @TableId
    private Long signId;

    /**
     * 员工id
     */
    private Integer staffId;

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

    public SignInForm() {
    }

    public SignInForm(Integer staffId, Long scheduleId, String status) {
        this.staffId = staffId;
        this.scheduleId = scheduleId;
        this.status = status;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}