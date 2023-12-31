package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
     * 消息id
     */
    @TableId
    private Long messageId;

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
     * 消息内容
     */
    private String content;


    public MessageForm() {
    }

    public MessageForm(String title, String type, Integer sendId, Date sendDate,  String content) {
        this.title = title;
        this.type = type;
        this.sendId = sendId;
        this.sendDate = sendDate;
        this.content = content;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}