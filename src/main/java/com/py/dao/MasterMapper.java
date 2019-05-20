package com.py.dao;

import com.py.bean.Master;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MasterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Master record);

    int insertSelective(Master record);

    Master selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Master record);

    int updateByPrimaryKey(Master record);

    List<Master> selectByExample(Master master);

    Master selectByPrimary(Master master);

    int selectMasterCount(@Param("account") String phonenum);
}