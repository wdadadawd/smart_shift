package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName staff_preference
 */
@TableName(value ="staff_preference")
@Data
public class StaffPreference implements Serializable {
    /**
     * 偏好所属员工id
     */
    private Integer staffId;

    /**
     * 偏好类型
     */
    private String type;

    /**
     * 偏好值
     */
    private String value;

    /**
     * 偏好详细
     */
    @TableField(exist = false)
    private String preferenceDetail;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}