package com.py.service;

import com.py.bean.Bulletin;
import com.py.bean.Message;
import com.py.dao.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;


    public List<Message> selectMessageByUserId(Integer userId) {
        return messageMapper.selectMessageByUserId(userId);
    }

    public List<Message> selectByExample(Message message) {
        return messageMapper.selectByExample(message);
    }

    public Message selectByPrimary(Message message) {
        return messageMapper.selectByPrimary(message);
    }


    public int deleteMessageById(Integer messageId) {
        return messageMapper.deleteByPrimaryKey(messageId);
    }




    /*******************   小程序---公告轮播消息   *****************************/

    public List<Bulletin> getBannerMessage(Message m) {
        List<Bulletin> mList = messageMapper.selectByExampleLimitFive(m);
        return mList;
    }
}
