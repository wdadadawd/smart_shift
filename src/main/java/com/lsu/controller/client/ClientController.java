package com.lsu.controller.client;

import com.lsu.common.R;
import com.lsu.entity.ClientInfo;
import com.lsu.entity.User;
import com.lsu.service.ClientInfoService;
import com.lsu.service.UserService;
import com.lsu.utils.RandomUtils;
import com.lsu.utils.RedisUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zt
 * @create 2023-07-27 22:39
 */
@RestController
public class ClientController {
    @Resource
    private ClientInfoService clientInfoService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserService userService;

    /**
     * 注册一个客户账号
     * @param user ()
     * @return
     */
    @RequiresRoles(value = {"client"},logical = Logical.OR)
    @PostMapping("/clientInfo")
    public R<String> insertClientInfo(@RequestBody User user, @RequestParam String email,@RequestParam String code){
        String mailCode = redisUtils.getMailCode(email, "register");
        if (mailCode==null)
            return R.err("验证码已失效");
        if (!mailCode.equals(code))
            return R.err("验证码错误");
        User userX = userService.getUserInfoByName(user.getUserName());
        if(userX != null)
            return R.err("用户名已存在");               //错误1
        if (userService.insertUser(user)>0){
            ClientInfo clientInfo = new ClientInfo(user.getId(), "用户:" + RandomUtils.getUUID(8), email, new Date());
            clientInfoService.save(clientInfo);
            return R.success("" + user.getId());                   //成功1
        }
        return R.err("注册失败");
    }

    /**
     * 修改客户的账号信息
     * @param clientInfo 客户信息()
     * @return
     */
    @PutMapping("/clientInfo")
    public R<String> updateClientInfo(@RequestBody ClientInfo clientInfo){

        return R.success(null);
    }




}
