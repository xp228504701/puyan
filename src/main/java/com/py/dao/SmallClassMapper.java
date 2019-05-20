package com.py.dao;

import com.py.bean.SmallClass;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmallClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmallClass record);

    int insertSelective(SmallClass record);

    SmallClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmallClass record);

    int updateByPrimaryKey(SmallClass record);

    int selectSmallClassByName(@Param("name") String name);

    List<SmallClass> selectByExample(SmallClass sc);

    List<SmallClass> selectSmallClassByMajorId(@Param("majorId") Integer majorId);
}