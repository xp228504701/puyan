package com.py.dao;

import com.py.bean.AdminRole;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);

    AdminRole selectByPrimary(AdminRole ar);
}