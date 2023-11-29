package com.lsu.controller.gpt;

import com.lsu.common.R;
import com.lsu.entity.Messages;
import com.lsu.service.MessagesService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zt
 * @create 2023-08-02 20:34
 */
@RestController
public class GptMessageController {
    @Resource
    private MessagesService messagesService;

    /**
     * 添加一条聊天
     * @param messages (role、content)
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner"},logical = Logical.OR)
    @PostMapping("/gptMessage")
    public R<String> insertGptMessage(@RequestBody Messages messages){
        if (messagesService.save(messages))
            return R.success("添加成功");
        return R.err("添加失败");
    }

    /**
     * 获取所有历史聊天记录
     * @return
     */
    @RequiresRoles(value = {"admin","shopowner","visitor"},logical = Logical.OR)
    @GetMapping("/gptMessage")
    public R<List<Messages>> getMessages(){
        List<Messages> messages = messagesService.getAllMessages();
        return R.success(messages);
    }
}
