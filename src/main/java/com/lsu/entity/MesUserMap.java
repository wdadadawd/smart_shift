package com.lsu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName mes_user_map
 */
@TableName(value ="mes_user_map")
@Data
public class MesUserMap implements Serializable {
    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 消息接收用户ID
     */
    private Integer receiveId;

    /**
     * 消息状态  1已读,0未读
     */
    private Boolean status;


    public MesUserMap() {
    }

    public MesUserMap(Long messageId, Integer receiveId) {
        this.messageId = messageId;
        this.receiveId = receiveId;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}