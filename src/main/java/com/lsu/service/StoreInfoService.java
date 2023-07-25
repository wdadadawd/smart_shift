package com.lsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsu.entity.StoreInfo;

import java.util.List;

/**
* @author 30567
* @description 针对表【store_info】的数据库操作Service
* @createDate 2023-03-12 14:59:29
*/
public interface StoreInfoService extends IService<StoreInfo> {
    /**
     * 获取全部门店信息(不包含门店规则)
     * @return  门店信息集合
     */
    List<StoreInfo> getAllStoreInfo();


    /**
     * 删除门店
     * @param id 门店id
     */
    Integer deleteStoreInfo(Integer id);

    /**
     * 添加门店
     * @param storeInfo 门店信息
     */
    Integer insertStoreInfo(StoreInfo storeInfo,Integer userId);

    /**
     * 修改门店信息
     * @param storeInfo 门店信息
     */
    Integer updateStoreInfo(StoreInfo storeInfo);

    /**
     * 获取全部门店的id和名称
     */
    List<StoreInfo> getALLStoreName();

    /**
     * 根据门店id获取指定门店信息
     * @return
     */
    StoreInfo getOneStore(Integer storeId);
}
