package com.py.service;

import com.py.bean.User;
import com.py.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public List<User> selectByExample(User user) {
        return userMapper.selectByExample(user);
    }

    public void userBlack(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    public User selectByPrimaryKey(Integer userId) {
       return userMapper.selectByPrimaryKey(userId);
    }

    public void insert(User u) {
        userMapper.insertSelective(u);
    }

    public User selectByAccount(String openId) {
        User u = new User();
        u.setAccount(openId);
        return userMapper.selectByPrimary(u);
    }

    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    public User selectUserIdByOpenId(String openId) {
        User u = new User();
        u.setAccount(openId);
        return userMapper.selectByPrimary(u);
    }
}
