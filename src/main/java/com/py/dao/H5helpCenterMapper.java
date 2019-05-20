package com.py.dao;

import com.py.bean.H5helpCenter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface H5helpCenterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(H5helpCenter record);

    int insertSelective(H5helpCenter record);

    H5helpCenter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(H5helpCenter record);

    int updateByPrimaryKey(H5helpCenter record);

    List<H5helpCenter> selectByExample(H5helpCenter h5helpCenter);
}