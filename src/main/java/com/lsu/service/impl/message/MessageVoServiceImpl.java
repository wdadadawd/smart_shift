package com.lsu.service.impl.message;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.vo.MessageVo;
import com.lsu.service.MessageVoService;
import com.lsu.mapper.message.MessageVoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 30567
* @description 针对表【message_vo】的数据库操作Service实现
* @createDate 2023-07-26 20:16:21
*/
@Service
public class MessageVoServiceImpl extends ServiceImpl<MessageVoMapper, MessageVo>
    implements MessageVoService{
    @Resource
    private MessageVoMapper messageVoMapper;

    /**
     * 获取收到的消息
     * @param userId 用户id
     * @return
     */
    @Override
    public List<MessageVo> getReceiveMessageForms(Integer userId) {
        QueryWrapper<MessageVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receive_id",userId);
        queryWrapper.orderByAsc("status");           //未读的消息放前面
        queryWrapper.orderByDesc("send_date");       //按发送时间降序
        return messageVoMapper.selectList(queryWrapper);
    }
}




