package com.lsu.service;

import com.lsu.entity.MesUserMap;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 30567
* @description 针对表【mes_user_map】的数据库操作Service
* @createDate 2023-07-26 20:43:40
*/
public interface MesUserMapService extends IService<MesUserMap> {

    //删除一条消息
    Integer deleteMesUserMap(MesUserMap mesUserMap);

    //读取消息
    Integer updateMesUserMap(MesUserMap mesUserMap);
}
