package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @TableName top_premiums
 */
@TableName(value ="top_premiums")
@Data
public class TopPremiums implements Serializable {
    /**
     * 排名
     */
    private String ranking;

    /**
     * 奖励
     */
    private Double premiums;

    /**
     * 奖励时间 年/月
     */
    private Date premiumsDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}