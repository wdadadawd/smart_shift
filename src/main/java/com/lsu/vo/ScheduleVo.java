package com.lsu.vo;

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
 * @TableName schedule_vo
 */
@TableName(value ="schedule_vo")
@Data
public class ScheduleVo implements Serializable {
    /**
     * 排次id
     */
    @TableId
    private Long scheduleId;

    /**
     * 门店id
     */
    private Integer storeId;

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
     * 班次所属职员的id
     */
    private Integer staffId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 职位
     */
    private String position;

    /**
     * 门店地址
     */
    private String address;

    /**
     * 门店名称
     */
    private String storeName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}