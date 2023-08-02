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
 * @TableName message_vo
 */
@TableName(value ="message_vo")
@Data
public class MessageVo implements Serializable {
    /**
     * 消息id
     */
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
     * 消息接收用户ID
     */
    private Integer receiveId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息状态  1已读,0未读
     */
    private Integer status;

    /**
     * 
     */
    private String sendName;

    /**
     * 姓名
     */
    private String receiveName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}