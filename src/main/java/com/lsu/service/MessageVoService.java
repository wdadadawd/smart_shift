package com.lsu.service;

import com.lsu.entity.MessageForm;
import com.lsu.vo.MessageVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 30567
* @description 针对表【message_vo】的数据库操作Service
* @createDate 2023-07-26 20:16:21
*/
public interface MessageVoService extends IService<MessageVo> {

    //获取收到的消息
    List<MessageVo> getReceiveMessageForms(Integer userId);
}
