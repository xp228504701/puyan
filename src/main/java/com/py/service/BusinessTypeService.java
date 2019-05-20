package com.py.service;

import com.py.bean.MajorClass;
import com.py.bean.SmallClass;
import com.py.dao.MajorClassMapper;
import com.py.dao.SmallClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessTypeService {
    @Autowired
    private MajorClassMapper majorClassMapper;
    @Autowired
    private SmallClassMapper smallClassMapper;


    public List<MajorClass> selectMajorClassByExample(MajorClass mc) {
        return majorClassMapper.selectByExample(mc);
    }

    public int selectMajorClassByName(String name) {
        return majorClassMapper.selectMajorClassByName(name);
    }

    public void saveMajorClass(MajorClass mc) {
        majorClassMapper.insertSelective(mc);
    }

    public void updateMajorClass(MajorClass mc) {
        majorClassMapper.updateByPrimaryKeySelective(mc);
    }

    public void deleteMajorClassByid(int id) {
        majorClassMapper.deleteByPrimaryKey(id);
    }

    public MajorClass selectByMajorClassPrimaryKey(int id) {
        return majorClassMapper.selectByPrimaryKey(id);
    }

    public List<MajorClass> selectSmallClassByName(String smallClassName) {
        return majorClassMapper.selectByName(smallClassName);
    }

    /*****   小类管理   *******/

    public List<SmallClass> selectSmallClassByExample(SmallClass sc) {
        return smallClassMapper.selectByExample(sc);

    }


    public int selectSmallClassName(String name) {
        return smallClassMapper.selectSmallClassByName(name);
    }

    public void saveSmallClass(SmallClass sc) {
        smallClassMapper.insertSelective(sc);
    }

    public void updateSmallClass(SmallClass sc) {
        smallClassMapper.updateByPrimaryKeySelective(sc);
    }

    public void deleteSmallClassByid(int id) {
        smallClassMapper.deleteByPrimaryKey(id);
    }


    public SmallClass selectBySmallClassPrimaryKey(int id) {
        return smallClassMapper.selectByPrimaryKey(id);
    }

    public List<MajorClass> selectMajorClassAllName() {
        return majorClassMapper.selectMajorClassAllName();
    }

    public List<SmallClass> selectSmallClassByMajorId(Integer majorId) {
        return smallClassMapper.selectSmallClassByMajorId(majorId);
    }

}
