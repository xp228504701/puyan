package com.py.dao;

import com.py.bean.MajorClass;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MajorClass record);

    int insertSelective(MajorClass record);

    MajorClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MajorClass record);

    int updateByPrimaryKey(MajorClass record);

    List<MajorClass> selectByExample(MajorClass mc);

    MajorClass selectByPrimary(MajorClass mc);


    int selectMajorClassByName(@Param("name") String name);

    List<MajorClass> selectMajorClassAllName();

    List<MajorClass> selectByName(@Param("smallClassName") String smallClassName);
}