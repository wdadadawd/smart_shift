package com.lsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsu.entity.ScheduleForm;
import com.lsu.entity.StaffInfo;
import com.lsu.vo.ScheduleVo;

import java.util.List;

/**
* @author 30567
* @description 针对表【staff_info】的数据库操作Service
* @createDate 2023-03-12 14:59:29
*/
public interface StaffInfoService extends IService<StaffInfo> {
    /**
     * 根据账号id获取用户信息
     * @param id 账号id
     * @return 对应的用户信息
     */
    StaffInfo getStaffInfo(Integer id);
    /**
     * 获取全部员工信息
     * @return 用户信息集合
     */
    List<StaffInfo> getStaffInfoList();
    /**
     * 添加员工信息
     * @param staffInfo 员工信息
     */
    Integer insertStaffInfo(StaffInfo staffInfo);

    /**
     * 删除员工信息
     * @param userId 员工账号id
     */
    Integer deleteStaffInfo(Integer userId);

    /**
     * 修改员工信息
     * @param staffInfo
     */
    Integer updateStaffInfo(StaffInfo staffInfo);

    /**
     * 获取对应门店人数
     * @param storeId 门店id
     * @return
     */
    Integer getStaffSumByStoreId(Integer storeId);

    /**
     * 根据用户id获取门店id
     * @return 门店id
     */
    Integer getStoreIdByUserId(Integer userId);

    /**
     * 获取门店可用的员工(排班)
     * @return
     */
    List<StaffInfo> getStaffListByStore(Integer storeId);

    /**
     * 获取合适的换班人员
     */
    List<StaffInfo> getSuitableStaff(ScheduleVo scheduleVo);

    //获取所有门店的员工id(除经理、店长)
    List<StaffInfo> getAllStaff();
}
