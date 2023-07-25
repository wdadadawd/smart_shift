package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

/**
 * 
 * @TableName position_map
 */
@TableName(value ="position_map")
@Data
public class PositionMap implements Serializable {
    /**
     * 职位id
     */
    @TableId
    private Integer positionId;

    /**
     * 职位名称
     */
    private String positionName;

    /**
     * 职位简介
     */
    private String positionIntroduction;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionMap that = (PositionMap) o;
        return Objects.equals(positionName, that.positionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionName);
    }

    public PositionMap(String positionName) {
        this.positionName = positionName;
    }

    public PositionMap() {
    }
}