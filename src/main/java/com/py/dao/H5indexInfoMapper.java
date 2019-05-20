package com.py.dao;

import com.py.bean.H5indexInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface H5indexInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(H5indexInfo record);

    int insertSelective(H5indexInfo record);

    H5indexInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(H5indexInfo record);

    int updateByPrimaryKey(H5indexInfo record);

    H5indexInfo selectByPrimary(H5indexInfo h5indexInfo);
}