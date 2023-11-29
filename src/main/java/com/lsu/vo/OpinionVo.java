package com.lsu.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName opinion_vo
 */
@TableName(value ="opinion_vo")
@Data
public class OpinionVo implements Serializable {
    /**
     * 意见id
     */
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

    /**
     * client客户、staff员工
     */
    private String type;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 
     */
    private String proposerName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}