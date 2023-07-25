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
 * @TableName leave_vo
 */
@TableName(value ="leave_vo")
@Data
public class LeaveVo implements Serializable {
    /**
     * 请假id
     */
    private Integer leaveId;

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
     * 门店id
     */
    private Integer storeId;

    /**
     * 门店名称
     */
    private String storeName;

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
     * 请假员工姓名
     */
    private String staffName;

    /**
     * 审批人姓名
     */
    private String aduitName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}