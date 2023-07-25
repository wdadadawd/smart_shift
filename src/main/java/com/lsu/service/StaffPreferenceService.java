package com.lsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsu.entity.StaffPreference;

import java.util.List;

/**
* @author 30567
* @description 针对表【staff_preference】的数据库操作Service
* @createDate 2023-03-12 14:59:29
*/
public interface StaffPreferenceService extends IService<StaffPreference> {
    /**
     * 根据员工id获取其全部偏好值
     * @param staffId 员工id
     * @return 当前员工的全部偏好信息
     */
    List<StaffPreference> getPreferenceListById(Integer staffId);

    /**
     * 根据员工id和偏好类型，获取对应的员工偏好信息
     * @param staffId 员工id
     * @param type 偏好类型
     * @return 偏好信息
     */
    StaffPreference getOnePreference(Integer staffId,String type);

    /**
     *  添加或修改偏好值
     * @param staffPreference 偏好信息
     */
    void updateOrInsertPreference(StaffPreference staffPreference);

}
