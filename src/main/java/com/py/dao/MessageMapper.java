package com.py.dao;

import com.py.bean.Bulletin;
import com.py.bean.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    List<Message> selectMessageByUserId(@Param("userId") Integer userId);

    List<Message> selectByExample(Message message);

    Message selectByPrimary(Message message);

    List<Bulletin> selectByExampleLimitFive(Message m);
}