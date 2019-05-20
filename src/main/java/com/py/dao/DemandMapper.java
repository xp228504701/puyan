package com.py.dao;

import com.py.bean.Demand;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Demand record);

    int insertSelective(Demand record);

    Demand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Demand record);

    int updateByPrimaryKey(Demand record);

    List<Demand> selectByExample(Demand demand);

    Demand selectByPrimary(Demand demand);

    int selectDemandName(@Param("labelName") String name);

    int selectDemandLabelId(@Param("labelId") String labelId);
}