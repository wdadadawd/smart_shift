package com.lsu.service;

import com.lsu.entity.Messages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 30567
* @description 针对表【messages】的数据库操作Service
* @createDate 2023-08-02 20:15:48
*/
public interface MessagesService extends IService<Messages> {
    //获取所有消息，按id排序
    List<Messages> getAllMessages();
}
