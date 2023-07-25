package com.lsu.service.impl.staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.ScheduleForm;
import com.lsu.entity.StaffInfo;
import com.lsu.mapper.staff.StaffInfoMapper;
import com.lsu.service.StaffInfoService;
import com.lsu.vo.ScheduleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 30567
* @description 针对表【staff_info】的数据库操作Service实现
* @createDate 2023-03-12 14:59:29
*/
@Service
@Transactional
public class StaffInfoServiceImpl extends ServiceImpl<StaffInfoMapper, StaffInfo>
    implements StaffInfoService{

    @Resource
    private StaffInfoMapper staffInfoMapper;

    /**
     * 根据账号id获取用户信息
     * @param id 账号id
     * @return 对应员工信息
     */
    @Override
    public StaffInfo getStaffInfo(Integer id) {
        StaffInfo staffInfo = staffInfoMapper.selectById(id);
        return  staffInfo;
    }

    /**
     * 获取全部员工信息
     * @return 员工信息集合
     */
    @Override
    public List<StaffInfo> getStaffInfoList() {
        List<StaffInfo> list = this.list();
        return list;
    }

    /**
     * 添加员工信息
     * @param staffInfo 员工信息
     */
    @Override
    public Integer insertStaffInfo(StaffInfo staffInfo) {
        staffInfo.setEntryTime(new Date());
        return staffInfoMapper.insert(staffInfo);
    }

    /**
     * 删除员工信息
     * @param userId 员工账号id
     */
    @Override
    public Integer deleteStaffInfo(Integer userId) {
        return staffInfoMapper.deleteById(userId);
    }

    /**
     * 修改员工信息
     * @param staffInfo 员工类
     */
    @Override
    public Integer updateStaffInfo(StaffInfo staffInfo) {
        return staffInfoMapper.updateById(staffInfo);
    }

    /**
     * 获取对应门店人数
     * @param storeId 门店id
     * @return
     */
    @Override
    public Integer getStaffSumByStoreId(Integer storeId) {
        return staffInfoMapper.getStaffSumByStoreId(storeId);
    }

    /**
     * 根据用户id获取门店id
     * @return 门店id
     */
    @Override
    public Integer getStoreIdByUserId(Integer userId) {
        return this.getStaffInfo(userId).getStoreId();
    }

    /**
     * 获取门店可用的员工(排班)
     * @return
     */
    @Override
    public List<StaffInfo> getStaffListByStore(Integer storeId) {
        QueryWrapper<StaffInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id","position","skill","age","sex","entry_time");
        queryWrapper.ne("position","店长");
        queryWrapper.eq("store_id",storeId);
        return staffInfoMapper.selectList(queryWrapper);
    }

    /**
     * 获取合适的换班人员
     * @param scheduleVo 班次信息
     * @return
     */
    @Override
    public List<StaffInfo> getSuitableStaff(ScheduleVo scheduleVo) {
        QueryWrapper<StaffInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id","name","position","skill","age","sex","entry_time");
        queryWrapper.ne("position","店长");
        queryWrapper.eq("store_id",scheduleVo.getStoreId());
        return staffInfoMapper.selectList(queryWrapper);
    }
}




