package com.py.service;

import com.py.bean.Admin;
import com.py.dao.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;


    public Admin selectByPrimary(Admin admin) {
        return adminMapper.selectByPrimary(admin);
    }

    public List<Admin> selectByExample(Admin admin) {
        return adminMapper.selectByExample(admin);
    }

    public int selectAccount(String adminAccount) {
        return adminMapper.selectAccount(adminAccount);
    }

    public void saveAdmin(Admin admin) {
        adminMapper.insertSelective(admin);
    }

    public void update(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void deleteAdminByid(int id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    public Admin selectByPrimaryKey(int id) {
        return adminMapper.selectByPrimaryKey(id);
    }
}
