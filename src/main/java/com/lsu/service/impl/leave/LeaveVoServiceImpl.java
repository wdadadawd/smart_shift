package com.lsu.service.impl.leave;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.vo.LeaveVo;
import com.lsu.service.LeaveVoService;
import com.lsu.mapper.leave.LeaveVoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author 30567
* @description 针对表【leave_vo】的数据库操作Service实现
* @createDate 2023-07-16 13:02:21
*/
@Service
public class LeaveVoServiceImpl extends ServiceImpl<LeaveVoMapper, LeaveVo>
    implements LeaveVoService{

    @Resource
    private LeaveVoMapper leaveVoMapper;

    /**
     * 分页搜索获取请假申请
     * @param current 页码
     * @param size 条数
     * @param key 关键字
     * @param startDate 筛选开始时间
     * @param endDate 筛选结束时间
     * @return
     */
    @Override
    public Page<LeaveVo> getLeaveVoPage(Integer current, Integer size, String key, Date startDate, Date endDate,Integer storeId) {
        QueryWrapper<LeaveVo> queryWrapper = new QueryWrapper<>();
        Page<LeaveVo> page = new Page<>(current, size);
        if (storeId!=null)             //筛选门店
            queryWrapper.eq("store_id",storeId);
        if (startDate!=null)            //筛选开始时间
            queryWrapper.ge("initiation_date",startDate);
        if (endDate != null)           //筛选结束时间
            queryWrapper.le("initiation_date",endDate);
        queryWrapper.and(q -> q.like("staff_name",key)        //设置模糊查询条件
                .or().like("leave_cause",key)
                .or().like("store_name",key)  //员工名称、请假原因、门店名称进行模糊查询
        );
//        queryWrapper.orderByAsc("start_time");          //按开始时间升序
        queryWrapper.last("order by case when aduit_status = '待审批' then 1 else 2 end, start_time asc");
        return leaveVoMapper.selectPage(page,queryWrapper);
    }
}




