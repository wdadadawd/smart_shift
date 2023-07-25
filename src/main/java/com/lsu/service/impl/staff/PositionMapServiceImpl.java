package com.lsu.service.impl.staff;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.PositionMap;
import com.lsu.service.PositionMapService;
import com.lsu.mapper.staff.PositionMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 30567
* @description 针对表【position_map】的数据库操作Service实现
* @createDate 2023-07-10 15:06:38
*/
@Service
public class PositionMapServiceImpl extends ServiceImpl<PositionMapMapper, PositionMap>
    implements PositionMapService{
    @Resource
    private PositionMapMapper positionMapMapper;
}




