package com.lsu.service.impl.client;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsu.entity.ClientInfo;
import com.lsu.service.ClientInfoService;
import com.lsu.mapper.client.ClientInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 30567
* @description 针对表【client_info】的数据库操作Service实现
* @createDate 2023-07-09 15:21:00
*/
@Service
public class ClientInfoServiceImpl extends ServiceImpl<ClientInfoMapper, ClientInfo>
    implements ClientInfoService{

}




