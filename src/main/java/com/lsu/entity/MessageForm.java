package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName message_form
 */
@TableName(value ="message_form")
@Data
public class MessageForm implements Serializable {
    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 消息发送人ID
     */
    private Integer sendId;

    /**
     * 消息发布日期
     */
    private Date sendDate;

    /**
     * 消息接收用户ID
     */
    private Integer receiveId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息状态
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}