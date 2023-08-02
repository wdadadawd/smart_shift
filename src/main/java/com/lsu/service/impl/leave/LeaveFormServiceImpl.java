package com.lsu.service.impl.leave;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.LeaveForm;
import com.lsu.entity.ScheduleForm;
import com.lsu.mapper.leave.LeaveFormMapper;
import com.lsu.service.LeaveFormService;
import com.lsu.utils.DateUtils;
import com.lsu.vo.SignInFormVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author 30567
* @description 针对表【leave_form】的数据库操作Service实现
* @createDate 2023-03-28 17:20:50
*/
@Service
public class LeaveFormServiceImpl extends ServiceImpl<LeaveFormMapper, LeaveForm>
    implements LeaveFormService{
    @Resource
    private LeaveFormMapper leaveFormMapper;


    /**
     * 判断该签到记录是否属于请假
     * @param scheduleForm 班次信息
     * @return
     */
    public Boolean isLeave(ScheduleForm scheduleForm){
        QueryWrapper<LeaveForm> queryWrapper = new QueryWrapper<>();
        //1.筛选员工id
        queryWrapper.eq("staff_id",scheduleForm.getStaffId());
        //2.筛选日期
        //获取签到的班次的开始时间和结束时间
        Date date = scheduleForm.getDate();
        Date startTime = scheduleForm.getStartTime();
        Date endTime = scheduleForm.getEndTime();
        //班次开始时间
        String startDate = DateUtils.getDate(date) + " " + DateUtils.getTime(startTime);
        //班次结束时间
        String endDate = DateUtils.getDate(date) + " " + DateUtils.getTime(endTime);
        //请假开始时间小于等于班次开始时间,请假结束时间大于等于班次结束时间
//        queryWrapper.le("start_time",startDate).gt("end_time",endDate);
        //只要不是班次结束时间 <= 请假开始时间,请假结束时间 <= 班次开始时间的签到都于该请假有关
        queryWrapper.not(q -> q.ge("start_time",endDate).or().le("end_time",startDate));
        //3.筛选状态
        queryWrapper.eq("aduit_status","已通过");
        Integer count = Math.toIntExact(leaveFormMapper.selectCount(queryWrapper));
//        System.out.println(count);
        return count > 0;
    }

    /**
     * 删除待审批的请假申请
     * @param leaveId 请假id
     * @return
     */
    @Override
    public Integer deleteLeaveForm(Integer leaveId) {
        QueryWrapper<LeaveForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("leave_id",leaveId);
        queryWrapper.eq("aduit_status","待审批");
        return leaveFormMapper.delete(queryWrapper);
    }
}




