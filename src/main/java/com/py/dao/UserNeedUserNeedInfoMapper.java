package com.py.dao;

import com.py.bean.UserNeedUserNeedInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNeedUserNeedInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserNeedUserNeedInfo record);

    int insertSelective(UserNeedUserNeedInfo record);

    UserNeedUserNeedInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserNeedUserNeedInfo record);

    int updateByPrimaryKey(UserNeedUserNeedInfo record);

    int deleteByUserNeedId(@Param("userNeedInfoId") Integer userNeedInfoId);
}