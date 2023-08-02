package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName opinion
 */
@TableName(value ="opinion")
@Data
public class Opinion implements Serializable {
    /**
     * 意见id
     */
    @TableId(type = IdType.AUTO)
    private Integer opinionId;

    /**
     * -1为匿名
     */
    private Integer proposerId;

    /**
     * 门店id
     */
    private Integer storeId;

    /**
     * 意见反馈的时间
     */
    private Date proposalTime;

    /**
     * 意见内容
     */
    private String opinionContent;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 意见发表的时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}