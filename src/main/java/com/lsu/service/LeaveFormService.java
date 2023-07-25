package com.lsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsu.entity.LeaveForm;
import com.lsu.entity.ScheduleForm;
import com.lsu.vo.SignInFormVo;

/**
* @author 30567
* @description 针对表【leave_form】的数据库操作Service
* @createDate 2023-03-28 17:20:50
*/
public interface LeaveFormService extends IService<LeaveForm> {
    //判断该签到记录是否属于请假
    Boolean isLeave(ScheduleForm scheduleForm);
}
