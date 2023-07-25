package com.lsu.service.impl.message;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.MessageForm;
import com.lsu.mapper.message.MessageFormMapper;
import com.lsu.service.MessageFormService;
import org.springframework.stereotype.Service;

/**
* @author 30567
* @description 针对表【message_form】的数据库操作Service实现
* @createDate 2023-05-18 10:36:09
*/
@Service
public class MessageFormServiceImpl extends ServiceImpl<MessageFormMapper, MessageForm>
    implements MessageFormService{

}




