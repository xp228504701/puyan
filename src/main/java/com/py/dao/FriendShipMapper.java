package com.py.dao;

import com.py.bean.FriendShip;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendShipMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FriendShip record);

    int insertSelective(FriendShip record);

    FriendShip selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FriendShip record);

    int updateByPrimaryKey(FriendShip record);

    List<FriendShip> selectByExample(FriendShip friendShip);
}