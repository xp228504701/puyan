package com.py.service;

import com.py.bean.Certification;
import com.py.dao.CertificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificationService {

    @Autowired
    private CertificationMapper certificationMapper;


    public void insertEntering(Certification userNeed) {
        certificationMapper.insertSelective(userNeed);
    }
}
