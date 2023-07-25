package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName passenger_flow
 */
@TableName(value ="passenger_flow")
@Data
public class PassengerFlow implements Serializable {
    /**
     * 客流量所属门店id
     */
    private Integer storeId;

    /**
     * 客流量日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;

    /**
     * 开始时间
     */
    @JsonFormat(pattern="HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="HH:mm:ss")
    @JsonProperty(value = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern="HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="HH:mm:ss")
    @JsonProperty(value = "end_time")
    private Date endTime;

    /**
     * 预测客流大小
     */
    private Double calculate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}