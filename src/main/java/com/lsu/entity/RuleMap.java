package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName rule_map
 */
@TableName(value ="rule_map")
@Data
public class RuleMap implements Serializable {
    /**
     * 规则类型
     */
    @TableId(type = IdType.INPUT)
    private String ruleType;

    /**
     * 规则详细
     */
    private String ruleDetail;

    /**
     * 规则名称
     */
    private String ruleName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}