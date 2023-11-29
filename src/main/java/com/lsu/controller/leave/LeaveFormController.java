package com.lsu.controller.leave;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsu.common.R;
import com.lsu.entity.*;
import com.lsu.service.*;
import com.lsu.utils.DateUtils;
import com.lsu.vo.LeaveVo;
import com.lsu.vo.SignInFormVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author zt
 * @create 2023-03-28 17:30
 * 管理请假操作
 */
@RestController
public class LeaveFormController {

    @Resource
    private LeaveFormService leaveFormService;

    @Resource
    private StaffInfoService staffInfoService;

    @Resource
    private LeaveVoService leaveVoService;

    @Resource
    private SignInFormService signInFormService;

    @Resource
    private ScheduleFormService scheduleFormService;

    @Resource
    private SeizeOrdersService seizeOrdersService;

    @Resource
    private MesUserMapService mesUserMapService;

    @Resource
    private MessageFormService messageFormService;


    /**
     * 发起请假申请
     * @param leaveForm (staffId,startTime,endTime,leaveCause)
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","normal"},logical = Logical.OR)
    @PostMapping("/applyLeave")
    public R<String> applyLeave(@RequestBody LeaveForm leaveForm){
        leaveForm.setInitiationDate(new Date());        //设置申请时间
        leaveForm.setAduitStatus("待审批");              //设置申请状态
        if (leaveFormService.save(leaveForm))
            return R.success("申请成功");
        return R.err("申请失败");
    }

    /**
     * 审批请假申请
     * @param leaveId 请假申请id
     * @param isPass  是否通过
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PostMapping("/leaveForm")
    public R<String> approvalLeave(@RequestParam Integer leaveId, @RequestParam Boolean isPass, HttpSession session){
        LeaveForm leaveForm = new LeaveForm();
        leaveForm.setLeaveId(leaveId);
        leaveForm.setAduitDate(new Date());
        LeaveForm leaveForm2 = leaveFormService.getById(leaveId);
        if (isPass){                     //审批通过
            leaveForm.setAduitStatus("已通过");
            StaffInfo staffInfo = staffInfoService.getStaffInfo(leaveForm2.getStaffId());
            //获取请假时间内的班次
            List<ScheduleForm> scheduleFormList = scheduleFormService.getScheduleFormByDate(leaveForm2.getStaffId(), leaveForm2.getStartTime(), leaveForm2.getEndTime());
            if (scheduleFormList!=null){
                for (ScheduleForm s:scheduleFormList){
                    //处理签到
                    SignInForm signInForm = new SignInForm(staffInfo.getUserId(),s.getScheduleId(),"已请假");
                    signInFormService.insertOrUpdateSign(signInForm);    //已经发布的就更新,未发布就发布
                    //发布抢单
                    Date date = new Date();
                    int compare = DateUtils.getDate(s.getDate()).compareTo(DateUtils.getDate(date));
                    if (compare>0||(compare==0&&DateUtils.getTimeDif(date,s.getStartTime())>0)){   //如果班次开始时间大于当前时间,则加入
                        SeizeOrders seizeOrders = new SeizeOrders(s.getStoreId(),"未发布",staffInfo.getPosition(),s.getScheduleId());
                        seizeOrdersService.insertOrUpdateSeizeOrders(seizeOrders);
                        s.setStaffId(null);
                        scheduleFormService.updateById(s);          //将该班次改为开放班次
                    }
                }
            }
        } else
            leaveForm.setAduitStatus("未通过");      //审批未通过
        //发布消息提示
        MessageForm messageForm = new MessageForm("请假审批结果", null, null, new Date(),
                "您在" + DateUtils.getDateTime(leaveForm2.getInitiationDate()) + "发起的请假申请已审批,结果为:" + leaveForm.getAduitStatus());
        messageFormService.save(messageForm);
        mesUserMapService.save(new MesUserMap(messageForm.getMessageId(),leaveForm2.getStaffId()));
        //更新数据库
        Integer userId = (Integer)session.getAttribute("userId");   //获取审批人id
        leaveForm.setAduitId(userId);                    //设置审批人id
        if (leaveFormService.updateById(leaveForm))
            return R.success("审批成功");
        return R.err("审批失败");
    }

    /**
     * 分页搜索获取请假申请
     * @param current 页码
     * @param size 条数
     * @param key 关键字
     * @param session 会话
     * @param startDate 筛选开始时间
     * @param endDate 筛选结束时间
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","visitor"},logical = Logical.OR)
    @GetMapping("/leaveList")
    public R<Page<LeaveVo>> getLeaveVo(@RequestParam Integer current, @RequestParam Integer size,
                                       @RequestParam(defaultValue = "") String key,HttpSession session,
                                       @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam(required = false) Date startDate,
                                       @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam(required = false) Date endDate){
        //对请求做分析(店长发起的/管理员发起的)
        Integer storeId = null;
        String role = (String) session.getAttribute("role");
        if ("shopowner".equals(role)) {
            Integer userId = (Integer) session.getAttribute("userId");    //获取店长id
            storeId = staffInfoService.getStoreIdByUserId(userId);    //获取店长所在门店id
        }
        return R.success(leaveVoService.getLeaveVoPage(current,size,key,startDate,endDate,storeId));
    }

    /**
     * 获取员工发起的请假申请
     * @param userId 用户id
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","normal"},logical = Logical.OR)
    @GetMapping("/leaveForm")
    public R<List<LeaveVo>> getAllLeaveVo(@RequestParam Integer userId){
        List<LeaveVo> leaveVoByUserId = leaveVoService.getLeaveVoByUserId(userId);
        return R.success(leaveVoByUserId);
    }


    /**
     * 撤回请假申请
     * @param leaveId 请假id
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","normal"},logical = Logical.OR)
    @DeleteMapping("/leaveForm")
    public R<String> deleteLeaveForm(@RequestParam Integer leaveId){
        String aduitStatus = leaveFormService.getById(leaveId).getAduitStatus();
        if (!aduitStatus.equals("待审批"))
            return R.success("请求已处理,撤销失败!");
        if (leaveFormService.deleteLeaveForm(leaveId) > 0)
            return R.success("撤销成功");
        else
            return R.success("撤销失败");
    }
}
