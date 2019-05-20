package com.py.service;

import com.py.bean.FriendShip;
import com.py.bean.H5helpCenter;
import com.py.bean.H5indexInfo;
import com.py.bean.H5newsInfo;
import com.py.dao.FriendShipMapper;
import com.py.dao.H5helpCenterMapper;
import com.py.dao.H5indexInfoMapper;
import com.py.dao.H5newsInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class H5IndexService {
    @Autowired
    private H5helpCenterMapper h5helpCenterMapper;
    @Autowired
    private H5indexInfoMapper h5indexInfoMapper;
    @Autowired
    private H5newsInfoMapper h5newsInfoMapper;
    @Autowired
    private FriendShipMapper friendShipMapper;



    public H5indexInfo selectAllIndexInfo() {
        return h5indexInfoMapper.selectByPrimary(new H5indexInfo());
    }

    public List<H5newsInfo> selectAllNewsInfo() {
        return h5newsInfoMapper.selectByExample(new H5newsInfo());
    }

    public List<H5helpCenter> selectAllHelpInfo() {
        return h5helpCenterMapper.selectByExample(new H5helpCenter());
    }

    public void update(H5indexInfo h5indexInfo) {
        h5indexInfoMapper.updateByPrimaryKeySelective(h5indexInfo);
    }

    public void insert(H5indexInfo h5indexInfo) {
        h5indexInfoMapper.insertSelective(h5indexInfo);
    }

    public void saveH5news(H5newsInfo h5newsInfo) {
        h5newsInfoMapper.insertSelective(h5newsInfo);
    }

    public void updateH5news(H5newsInfo h5newsInfo) {
        h5newsInfoMapper.updateByPrimaryKeySelective(h5newsInfo);
    }

    public void deleteH5newsByid(int id) {
        h5newsInfoMapper.deleteByPrimaryKey(id);
    }
    public H5newsInfo selectH5NewsByPrimaryKey(Integer helpId) {
        return h5newsInfoMapper.selectByPrimaryKey(helpId);
    }


    public void saveH5HelpInfo(H5helpCenter h5helpCenter) {
        h5helpCenterMapper.insertSelective(h5helpCenter);
    }
    public void updateH5HelpInfo(H5helpCenter h5helpCenter) {
        h5helpCenterMapper.updateByPrimaryKeySelective(h5helpCenter);
    }

    public void deleteH5HelpInfoByid(int id) {
        h5helpCenterMapper.deleteByPrimaryKey(id);
    }


    public H5helpCenter selectH5HelpByPrimaryKey(Integer helpId) {
        return h5helpCenterMapper.selectByPrimaryKey(helpId);
    }




    /***********************  友情链接  ******************************/


    public List<FriendShip> selectFriendShipByExample() {
        return friendShipMapper.selectByExample(new FriendShip());
    }

    public FriendShip selectLinkByPrimaryKey(int id) {
        return friendShipMapper.selectByPrimaryKey(id);
    }

    public void saveFriendLinks(FriendShip friendShip) {
        friendShipMapper.insertSelective(friendShip);
    }

    public void deleteFriendLinksByid(int id) {
        friendShipMapper.deleteByPrimaryKey(id);
    }

    public void updateLinksInfo(FriendShip friendShip) {
        friendShipMapper.updateByPrimaryKeySelective(friendShip);
    }
}
