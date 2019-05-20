package com.py.service;

import com.py.bean.AdminRole;
import com.py.bean.Menu;
import com.py.bean.Role;
import com.py.dao.AdminRoleMapper;
import com.py.dao.MenuMapper;
import com.py.dao.RoleMapper;
import com.py.dao.RoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleMenuService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;




    public List<Role> selectAllRoleList(Role role) {
        return roleMapper.selectByExample(role);
    }


    public Role selectByPrimary(Role role) {
        return roleMapper.selectByPrimary(role);
    }

    public int saveRole(Role role) {
        return roleMapper.insertSelective(role);
    }

    public int update(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    public Role checkRoleName(Role role) {
        return roleMapper.selectByPrimary(role);
    }

    public int deleteRole(int id) {

        AdminRole ar = new AdminRole();
        ar.setRoleId(id);
        AdminRole adminRole = adminRoleMapper.selectByPrimary(ar);
        if (adminRole != null){
            return -100;
        }
        roleMenuMapper.deleteByRoleId(id);
        return roleMapper.deleteByPrimaryKey(id);
    }






    /***********************  菜单管理  ****************************/


    /**
     * 根据父类id查找子类菜单
     * @param parseInt
     * @return
     */
    public List<Menu> selectSonNameById(int parseInt) {
        return menuMapper.selectSonNameById(parseInt);
    }


    /**
     * 查找所有父类菜单
     * @return
     */
    public List<Menu> selectFatherName() {
        return menuMapper.selectFatherName();
    }



    public List<Menu> selectMenuByExample(Menu menu) {
        return menuMapper.selectByExample(menu);
    }

    public void saveMenu(Menu menu) {
        menuMapper.insertSelective(menu);
    }

    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    public void deleteMenuByid(int id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    public List<Menu> selectSonName() {
        return menuMapper.selectSonName();
    }

    public Menu selectMenuByPrimaryId(int id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    public List<Menu> selectMenuByPath(String path) {
        return menuMapper.selectMenuByPath(path);
    }

    public int selectMenuByName(String MenuName) {
        return menuMapper.selectByName(MenuName);
    }
}
