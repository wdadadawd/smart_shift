package com.lsu.service.impl.staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.mapper.staff.StaffInfoVoMapper;
import com.lsu.service.StaffInfoVoService;
import com.lsu.vo.StaffInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 30567
* @description 针对表【staff_info_vo】的数据库操作Service实现
* @createDate 2023-03-27 21:34:41
*/
@Service
public class StaffInfoVoServiceImpl extends ServiceImpl<StaffInfoVoMapper, StaffInfoVo>
    implements StaffInfoVoService{

    @Resource
    private  StaffInfoVoMapper staffInfoVoMapper;

    /**
     * 搜索员工信息
     * @param current 页码
     * @param size 条数
     * @param key 关键字
     * @param storeId 店长所属门店id
     * @return 对应的分页信息
     */
    @Override
    public Page<StaffInfoVo> selectStaffInfoVo(Integer current, Integer size, String key,Integer storeId,Boolean sort) {
        Page<StaffInfoVo> p = new Page<>(current,size);            //创建分页类
        QueryWrapper<StaffInfoVo> queryWrapper = new QueryWrapper<StaffInfoVo>();
        if (storeId!=null)                              //店长只获取本店的人员
            queryWrapper.eq("store_id",storeId);
        queryWrapper.and(q -> q.like("user_name",key)        //设置查询条件
                        .or().like("name",key)
                        .or().like("phone",key)
                        .or().like("position",key)
                        .or().like("store_name",key)
                        .or().like("email",key)
                        .or().like("age",key)
                        .or().like("skill",key)
                        .or().like("sex",key)    //对账号,员工姓名,电话,邮箱,职位,所属门店名称,年龄,技能,性别进行模糊查询
                );
        if (sort!=null){                 //判断是否需要根据入职时间排序
            if (sort)
                queryWrapper.orderByAsc("entry_time");
            else
                queryWrapper.orderByDesc("entry_time");
        }
        return staffInfoVoMapper.selectPage(p,queryWrapper);
    }
}




