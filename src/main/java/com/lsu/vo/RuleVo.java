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
 * @TableName rule_vo
 */
@TableName(value ="rule_vo")
@Data
public class RuleVo implements Serializable {
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
    private Integer status;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 更新人id
     */
    private Integer userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 规则详细
     */
    private String ruleDetail;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}