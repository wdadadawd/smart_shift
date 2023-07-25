package com.lsu.mapper.staff;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsu.entity.StaffInfo;
import org.apache.ibatis.annotations.Select;

/**
* @author 30567
* @description 针对表【staff_info】的数据库操作Mapper
* @createDate 2023-03-12 14:59:29
* @Entity generator.com.lsu.entity.StaffInfo
*/
public interface StaffInfoMapper extends BaseMapper<StaffInfo> {
    @Select("SELECT COUNT(*) FROM staff_info WHERE store_id = #{storeId};")
    Integer getStaffSumByStoreId(Integer storeId);

    @Select("UPDATE staff_info SET store_id = NULL WHERE store_id = #{storeId};")
    void updateStoreId(Integer storeId);
}




