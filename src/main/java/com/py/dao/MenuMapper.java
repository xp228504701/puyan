package com.py.dao;

import com.py.bean.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> selectFatherName();

    List<Menu> selectSonNameById(@Param("superiorId") int parseInt);

    List<Menu> selectSonName();

    List<Menu> selectByExample(Menu menu);

    List<Menu> selectMenuByPath(@Param("path") String path);

    Menu selectByPrimary(Menu menu);

    int selectByName(@Param("menuName") String menuName);
}