package com.lsu.service.impl.staff;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.StaffPreference;
import com.lsu.mapper.staff.StaffPreferenceMapper;
import com.lsu.service.StaffPreferenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 30567
* @description 针对表【staff_preference】的数据库操作Service实现
* @createDate 2023-03-12 14:59:29
*/
@Service
@Transactional
public class StaffPreferenceServiceImpl extends ServiceImpl<StaffPreferenceMapper, StaffPreference>
    implements StaffPreferenceService{

    @Resource
    private StaffPreferenceMapper staffPreferenceMapper;

    /**
     * 根据员工id获取其全部偏好值
     * @param staffId 员工id
     * @return 当前员工的全部偏好信息
     */
    @Override
    public List<StaffPreference> getPreferenceListById(Integer staffId) {
        QueryWrapper<StaffPreference> staffPreferenceQueryWrapper = new QueryWrapper<>();
        staffPreferenceQueryWrapper.eq("staff_id",staffId);
        List<StaffPreference> staffPreferences = staffPreferenceMapper.selectList(staffPreferenceQueryWrapper);
        for (int i=0;i<staffPreferences.size();i++){
            StaffPreference staffPreference = staffPreferences.get(i);
            switch (staffPreference.getType()){
                case "day":staffPreference.setPreferenceDetail("工作日偏好");break;
                case "time":staffPreference.setPreferenceDetail("工作时间偏好");break;
                case "daylong":
                case "weekdaylong":
                    staffPreference.setPreferenceDetail("班次时间偏好");break;
            }
        }
        return staffPreferences;
    }

    /**
     * 根据员工id和偏好类型，获取对应的员工偏好信息
     * @param staffId 员工id
     * @param type 偏好类型
     * @return 偏好信息
     */
    @Override
    public StaffPreference getOnePreference(Integer staffId, String type) {
        QueryWrapper<StaffPreference> staffPreferenceQueryWrapper = new QueryWrapper<>();
        staffPreferenceQueryWrapper.eq("staff_id",staffId)
                .eq("type",type);
        return staffPreferenceMapper.selectOne(staffPreferenceQueryWrapper);
    }

    /**
     *  添加或修改偏好值
     * @param staffPreference 偏好信息
     */
    @Override
    public void updateOrInsertPreference(StaffPreference staffPreference) {
        StaffPreference onePreference = this.getOnePreference(staffPreference.getStaffId(), staffPreference.getType());
        if(onePreference == null){         //判断是否存在该偏好
            staffPreferenceMapper.insert(staffPreference);
        }else{
            QueryWrapper<StaffPreference> staffPreferenceQueryWrapper = new QueryWrapper<>();
            staffPreferenceQueryWrapper.eq("staff_id",staffPreference.getStaffId())
                    .eq("type",staffPreference.getType());
            staffPreferenceMapper.update(staffPreference,staffPreferenceQueryWrapper);
        }
    }
}




