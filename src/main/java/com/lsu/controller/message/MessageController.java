package com.lsu.controller.message;

import com.lsu.common.R;
import com.lsu.entity.MesUserMap;
import com.lsu.entity.MessageForm;
import com.lsu.entity.StaffInfo;
import com.lsu.service.MesUserMapService;
import com.lsu.service.MessageFormService;
import com.lsu.service.MessageVoService;
import com.lsu.service.StaffInfoService;
import com.lsu.vo.MessageVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zt
 * @create 2023-07-26 19:34
 */
@RestController
public class MessageController {
    @Resource
    private MessageFormService messageFormService;

    @Resource
    private StaffInfoService staffInfoService;

    @Resource
    private MessageVoService messageVoService;

    @Resource
    private MesUserMapService mesUserMapService;

    /**
     * 对指定门店发布一条信息
     * @param messageForm (title,sendId,content) type
     * @return
     */
    @RequiresRoles(value = {"shopowner","admin"},logical = Logical.OR)
    @PostMapping("/messageForm")
    public R<String> insertMessageForm(@RequestBody MessageForm messageForm, @RequestParam Integer storeId){
        List<StaffInfo> staffListByStore = staffInfoService.getStaffListByStore(storeId);
        messageForm.setSendDate(new Date());
        messageForm.setType("公告");
        messageFormService.save(messageForm);               //保存消息
        MesUserMap mesUserMap = new MesUserMap(messageForm.getMessageId(), null);
        for (StaffInfo staffInfo: staffListByStore){
            mesUserMap.setReceiveId(staffInfo.getUserId());
            mesUserMapService.save(mesUserMap);                 //保存到映射表中
        }
        return R.success("发布成功");
    }

    /**
     * 删除一条接收到的消息
     * @param mesUserMap 消息信息(receiveId,messageId)
     * @return
     */
    @DeleteMapping("/messageForm")
    @RequiresRoles(value = {"shopowner","admin","normal"},logical = Logical.OR)
    public R<String> deleteMessageForm(@RequestBody MesUserMap mesUserMap){
        if (mesUserMapService.deleteMesUserMap(mesUserMap)>0)
            return R.success("删除成功");
        return R.success("删除失败");
    }


    /**
     * 获取发出的消息
     * @param userId 用户id
     * @return 发出的消息集合
     */
    @GetMapping("/sendMessageForm")
    @RequiresRoles(value = {"shopowner","admin"},logical = Logical.OR)
    public R<List<MessageForm>> getSendMessageForm(@RequestParam Integer userId){
        List<MessageForm> sendMessageForm = messageFormService.getSendMessageForm(userId);
        return R.success(sendMessageForm);
    }


    /**
     * 获取接收到的消息
     * @param userId 用户id
     * @return 接收的消息集合
     */
    @GetMapping("/receiveMessageForm")
    @RequiresRoles(value = {"shopowner","admin","normal"},logical = Logical.OR)
    public R<List<MessageVo>> getReceiveMessageForm(@RequestParam Integer userId){
        List<MessageVo> receiveMessageForms = messageVoService.getReceiveMessageForms(userId);
        return R.success(receiveMessageForms);
    }

    /**
     * 读取一条消息
     * @param mesUserMap 消息信息(receiveId,messageId)
     * @return
     */
    @PutMapping("/messageForm")
    public R<String> updateMessageForm(@RequestBody MesUserMap mesUserMap){
        if (mesUserMapService.updateMesUserMap(mesUserMap) > 0)
            return R.success("读取成功");
        return R.success("读取失败");
    }
}
