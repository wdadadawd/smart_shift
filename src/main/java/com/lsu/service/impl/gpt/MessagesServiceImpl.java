package com.lsu.service.impl.gpt;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.Messages;
import com.lsu.service.MessagesService;
import com.lsu.mapper.message.MessagesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 30567
* @description 针对表【messages】的数据库操作Service实现
* @createDate 2023-08-02 20:15:48
*/
@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages>
    implements MessagesService{

    @Resource
    private MessagesMapper messagesMapper;

    /**
     * 获取全部历史消息，按id(时间)升序
     * @return
     */
    @Override
    public List<Messages> getAllMessages() {
        QueryWrapper<Messages> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return messagesMapper.selectList(queryWrapper);
    }
}




