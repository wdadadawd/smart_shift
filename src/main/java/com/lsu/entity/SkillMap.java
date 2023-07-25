package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName skill_map
 */
@TableName(value ="skill_map")
@Data
public class SkillMap implements Serializable {
    /**
     * 技能id
     */
    @TableId
    private Integer skillId;

    /**
     * 技能名称
     */
    private String skillName;

    /**
     * 技能简介
     */
    private String skillIntroduction;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}