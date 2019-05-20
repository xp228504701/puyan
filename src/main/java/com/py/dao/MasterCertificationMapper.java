package com.py.dao;

import com.py.bean.MasterCertification;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MasterCertificationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MasterCertification record);

    int insertSelective(MasterCertification record);

    MasterCertification selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MasterCertification record);

    int updateByPrimaryKey(MasterCertification record);

    List<MasterCertification> selectByMasterId(@Param("masterId") Integer parseInt);
}