package com.py.controller.api;

import com.py.bean.Bulletin;
import com.py.bean.Message;
import com.py.service.MessageService;
import com.py.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageApiController {
    @Autowired
    private MessageService messageService;


    /**
     * 根据用户id获取消息内容
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getMessageByUserId",method = RequestMethod.POST)
    public Msg getMessageByUserId(@RequestParam("userId")Integer userId){
        if(userId == null || "".equals(userId)){
            return Msg.fail().add("msg","参数有误");
        }
        List<Message> messageList = messageService.selectMessageByUserId(userId);
        return Msg.success().add("list",messageList);
    }

    /**
     * 根据id删除消息
     * @param messageId
     * @return
     */
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public Msg deleteByUserId(@RequestParam("messageId")Integer messageId){
        int i = messageService.deleteMessageById(messageId);
        if (i <= 0){
            return Msg.fail();
        }
        return Msg.success();
    }



    /*******************   小程序---公告轮播消息   *****************************/

    /**
     * 获取轮播的公告消息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBannerMessage",method = RequestMethod.POST)
    public Msg getBannerMessage(HttpServletRequest request){

        Message m = new Message();
        m.setMessageType(2);
        List<Bulletin> mList= messageService.getBannerMessage(m);
        return Msg.success().add("list",mList);

    }



}
