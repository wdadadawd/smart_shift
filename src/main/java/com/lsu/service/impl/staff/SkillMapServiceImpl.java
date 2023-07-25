package com.lsu.service.impl.staff;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.SkillMap;
import com.lsu.service.SkillMapService;
import com.lsu.mapper.staff.SkillMapMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 30567
* @description 针对表【skill_map】的数据库操作Service实现
* @createDate 2023-07-10 15:06:38
*/
@Service
public class SkillMapServiceImpl extends ServiceImpl<SkillMapMapper, SkillMap>
    implements SkillMapService{
    @Resource
    private SkillMapMapper skillMapMapper;

}




