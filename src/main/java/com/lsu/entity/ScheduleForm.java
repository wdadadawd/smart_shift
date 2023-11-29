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
 * @TableName schedule_form
 */
@TableName(value ="schedule_form")
@Data
public class ScheduleForm implements Serializable {
    /**
     * id
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

    public ScheduleForm() {
    }

    public ScheduleForm(Integer storeId, Date date, Date startTime, Date endTime, Integer staffId) {
        this.storeId = storeId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.staffId = staffId;
    }

    public ScheduleForm(Long scheduleId, Integer staffId) {
        this.scheduleId = scheduleId;
        this.staffId = staffId;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}