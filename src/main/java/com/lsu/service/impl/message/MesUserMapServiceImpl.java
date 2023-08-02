package com.lsu.service.impl.message;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.MesUserMap;
import com.lsu.service.MesUserMapService;
import com.lsu.mapper.message.MesUserMapMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 30567
* @description 针对表【mes_user_map】的数据库操作Service实现
* @createDate 2023-07-26 20:43:40
*/
@Service
public class MesUserMapServiceImpl extends ServiceImpl<MesUserMapMapper, MesUserMap>
    implements MesUserMapService{

    @Resource
    private MesUserMapMapper mesUserMapMapper;

    /**
     * 删除一条消息
     * @param mesUserMap 消息信息
     * @return
     */
    @Override
    public Integer deleteMesUserMap(MesUserMap mesUserMap) {
        QueryWrapper<MesUserMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id",mesUserMap.getMessageId());
        queryWrapper.eq("receive_id",mesUserMap.getReceiveId());
        return mesUserMapMapper.delete(queryWrapper);
    }

    /**
     * 读取消息
     * @param mesUserMap 消息信息
     * @return
     */
    @Override
    public Integer updateMesUserMap(MesUserMap mesUserMap) {
        mesUserMap.setStatus(true);
        QueryWrapper<MesUserMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id",mesUserMap.getMessageId());
        queryWrapper.eq("receive_id",mesUserMap.getReceiveId());
        return mesUserMapMapper.update(mesUserMap,queryWrapper);
    }
}




