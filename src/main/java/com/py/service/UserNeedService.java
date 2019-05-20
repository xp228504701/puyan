package com.py.service;

import com.py.bean.UserNeed;
import com.py.bean.UserNeedInfo;
import com.py.bean.UserNeedUserNeedInfo;
import com.py.dao.UserNeedInfoMapper;
import com.py.dao.UserNeedMapper;
import com.py.dao.UserNeedUserNeedInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserNeedService {
    @Autowired
    private UserNeedMapper userNeedMapper;
    @Autowired
    private UserNeedInfoMapper userNeedInfoMapper;
    @Autowired
    private UserNeedUserNeedInfoMapper needUserNeedInfoMapper;



    /**************    -用户的需求-   *******************/

    public int insertUserNeed(UserNeed userNeed) {
        return userNeedMapper.insertSelective(userNeed);
    }


    public List<UserNeed> selectByOrderNo(String orderNo) {
        UserNeed uu = new UserNeed();
        uu.setParam2(orderNo);
        return userNeedMapper.selectByExample(uu);
    }



    /**************    -用户的需求固定信息-   *******************/
    public void insertUserNeedInfo(UserNeedInfo uni) {
        userNeedInfoMapper.insertSelective(uni);
    }



    /**************    -用户的需求固定信息关联 -   *******************/
    public void insertUserNeedInfoAndUserNeed(UserNeedUserNeedInfo unin) {
        needUserNeedInfoMapper.insertSelective(unin);
    }


    /**
     * 如果添加数据有一条失败则删除之前的数据
     */
    public void deleteAllById(Integer userNeedInfoId) {

        //删除关联表
        needUserNeedInfoMapper.deleteByUserNeedId(userNeedInfoId);
        //删除固定信息表
        userNeedInfoMapper.deleteByPrimaryKey(userNeedInfoId);

    }


}
