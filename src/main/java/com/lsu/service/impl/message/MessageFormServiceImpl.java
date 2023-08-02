package com.lsu.service.impl.message;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.MessageForm;
import com.lsu.mapper.message.MessageFormMapper;
import com.lsu.service.MessageFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 30567
* @description 针对表【message_form】的数据库操作Service实现
* @createDate 2023-05-18 10:36:09
*/
@Service
public class MessageFormServiceImpl extends ServiceImpl<MessageFormMapper, MessageForm>
    implements MessageFormService{

    @Resource
    private MessageFormMapper messageFormMapper;

    /**
     * 获取发出的消息
     * @param userId 用户id
     * @return
     */
    @Override
    public List<MessageForm> getSendMessageForm(Integer userId) {
        QueryWrapper<MessageForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("send_id",userId);
        queryWrapper.orderByDesc("send_date");
        return messageFormMapper.selectList(queryWrapper);
    }
}




