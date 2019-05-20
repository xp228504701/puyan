package com.py.dao;

import com.py.bean.UserNeedInfo;

public interface UserNeedInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserNeedInfo record);

    int insertSelective(UserNeedInfo record);

    UserNeedInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserNeedInfo record);

    int updateByPrimaryKey(UserNeedInfo record);
}