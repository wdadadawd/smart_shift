package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName stroe_rule
 */
@TableName(value ="store_rule")
@Data
public class StoreRule implements Serializable {
    /**
     * 规则所属门店id
     */
    private Integer storeId;

    /**
     * 规则类型
     */
    private String type;

    /**
     * 规则值
     */
    private String value;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人id
     */
    private Integer userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}