package com.lsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsu.entity.MessageForm;

import java.util.List;

/**
* @author 30567
* @description 针对表【message_form】的数据库操作Service
* @createDate 2023-05-18 10:36:09
*/
public interface MessageFormService extends IService<MessageForm> {
    //获取发出的消息
    List<MessageForm> getSendMessageForm(Integer userId);

}
