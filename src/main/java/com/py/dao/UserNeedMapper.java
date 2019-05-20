package com.py.dao;

import com.py.bean.UserNeed;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNeedMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserNeed record);

    int insertSelective(UserNeed record);

    UserNeed selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserNeed record);

    int updateByPrimaryKey(UserNeed record);

    List<UserNeed> selectByExample(UserNeed uu);
}