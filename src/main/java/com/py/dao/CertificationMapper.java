package com.py.dao;

import com.py.bean.Certification;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Certification record);

    int insertSelective(Certification record);

    Certification selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Certification record);

    int updateByPrimaryKey(Certification record);
}