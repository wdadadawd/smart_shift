package com.lsu.entity;

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
 * @TableName leave_form
 */
@TableName(value ="leave_form")
@Data
public class LeaveForm implements Serializable {

    /**
     * 请假id
     */
    @TableId
    private Integer leaveId;

    /**
     * 员工id
     */
    private Integer staffId;

    /**
     * 申请发起时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date initiationDate;

    /**
     * 请假开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 请假结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 请假原因
     */
    private String leaveCause;

    /**
     * 审核时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date aduitDate;

    /**
     * 审核状态
     */
    private String aduitStatus;

    /**
     * 审批人id
     */
    private Integer aduitId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}