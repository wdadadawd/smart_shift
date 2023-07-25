package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lsu.vo.RuleVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName store_info
 */
@TableName(value ="store_info")
@Data
public class StoreInfo implements Serializable {
    /**
     * 门店id，唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 门店名称
     */
    private String name;

    /**
     * 门店地址
     */
    private String address;

    /**
     * 门店面积(平方米)
     */
    private Double size;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 员工人数
     */
    @TableField(exist = false)
    private Integer staffSum;

    /**
     * 门店规则
     */
    @TableField(exist = false)
    private List<RuleVo> storeRules;

    public StoreInfo() {
    }

    public StoreInfo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}