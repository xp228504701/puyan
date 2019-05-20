package com.py.dao;

import com.py.bean.H5newsInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface H5newsInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(H5newsInfo record);

    int insertSelective(H5newsInfo record);

    H5newsInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(H5newsInfo record);

    int updateByPrimaryKey(H5newsInfo record);

    H5newsInfo selectByPrimary(H5newsInfo h5newsInfo);

    List<H5newsInfo> selectByExample(H5newsInfo h5newsInfo);
}