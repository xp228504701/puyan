package com.py.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.py.bean.Admin;
import com.py.bean.Menu;
import com.py.bean.Role;
import com.py.service.RoleMenuService;
import com.py.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RoleMenuController extends BaseController{
    @Autowired
    private RoleMenuService roleMenuService;




    /**   ---------角色管理-------------     */

    /**
     * 获取role信息
     * @param request
     * @return
     */
    @RequestMapping("/getRoleAll")
    public Map<String,Object> getRoleAll(HttpServletRequest request) {


        Role role = new Role();
        //获取分页和排序条件
        LayerPage(request);
        //返回map
        Map<String,Object> resultMap = new HashMap<>();
        //Page<?> page = PageHelper.startPage(pageNum, pageSize);

        List<Role> roleList = roleMenuService.selectAllRoleList(role);
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("data", roleList);
        return resultMap;

    }



    /**
     * 保存role
     * @param request
     * @return
     */
    @RequestMapping("/saveRole")
    public Map<String, Object> saveRole(HttpServletRequest request,@ModelAttribute("obj") Role role){

        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        role.setCreateTime(new Date());
        roleMenuService.saveRole(role);
        resultMap.put("type", "add");
        resultMap.put("code", 1);
        return resultMap;
    }




    /**
     * 修改role
     * @param request
     * @return
     */
    @RequestMapping("/updateRole")
    public Map<String, Object> updateRole(HttpServletRequest request,@ModelAttribute("obj") Role role){

        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        roleMenuService.update(role);
        resultMap.put("type", "update");
        resultMap.put("code", 1);
        return resultMap;
    }


    /**
     * 检查名称是否存在
     * @param roleName
     * @return
     */
    @RequestMapping("/checkRoleName")
    public Map<String, Object> checkRoleNamu(@RequestParam("roleName")String roleName){
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        Role role = new Role();
        role.setName(roleName);
        Role a = roleMenuService.checkRoleName(role);
        if (a != null){
            resultMap.put("code",2);
            resultMap.put("msg","名称已存在");
        }else{
            resultMap.put("code",1);
        }
        return resultMap;
    }


    /**
     * 删除Role
     * @return
     */
    @RequestMapping("/deleteRole")
    public Map<String, Object> deleteRole(HttpServletRequest request, Model model){

        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        roleMenuService.deleteRole(id);
        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }







    /**   ---------菜单管理-------------     */

    /**
     * 获取父级菜单
     * @return
     */
    @RequestMapping(value="/getFatherMenu")
    public Map<String,Object> getFatherMenu(HttpServletRequest request) {

        //返回map
        Map<String,Object> resultMap = new HashMap<>();

        List<Menu> fatherName = roleMenuService.selectFatherName();
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("data", fatherName);
        return resultMap;
    }


    /**
     * 根据父级菜单id获取子级菜单
     * @return
     */
    @RequestMapping(value="/getSonMenuByFartherId")
    public Map<String,Object> getSonMenuByFartherId(HttpServletRequest request) {
        //返回map
        Map<String,Object> resultMap = new HashMap<>();
        String superiorId = request.getParameter("superiorId");
        List<Menu> sanName = roleMenuService.selectSonNameById(Integer.parseInt(superiorId));
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("data", sanName);
        return resultMap;
    }


    /**
     * 获取菜单
     * @param request
     * @return
     */
    @RequestMapping("/getMenu")
    public Map<String,Object> getMenu(HttpServletRequest request) {

        Menu menu = new Menu();
        //获取分页和排序条件
        LayerPage(request);

        //排序插件
        PageHelper.orderBy(orderTable+" "+orderStyle);

        //返回map
        Map<String,Object> resultMap = new HashMap<>();
        Page<?> page = PageHelper.startPage(pageNum, pageSize);

        List<Menu> menuList = roleMenuService.selectMenuByExample(menu);
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("count", page.getTotal());
        resultMap.put("data", menuList);
        return resultMap;

    }


    /**
     * 新增菜单
     * @param request
     * @return
     */
    @RequestMapping(value="saveMenu")
    public Map<String, Object> saveMenu(HttpServletRequest request,@ModelAttribute("obj") Menu menu) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int m = roleMenuService.selectMenuByName(menu.getName());
       if(m > 0) {
            resultMap.put("code", 2);
            resultMap.put("type", "add");
            return resultMap;
        }
        List<Menu> menuList = roleMenuService.selectMenuByPath(menu.getPath());
        if(menuList.size() != 0 && !menuList.isEmpty()){
            resultMap.put("code", 3);
            resultMap.put("type", "add");
            return resultMap;
        }
        menu.setTime(new Date());
        roleMenuService.saveMenu(menu);
        resultMap.put("type", "add");
        resultMap.put("code", 1);
        return resultMap;
    }


    /**
     * 修改菜单
     * @param request
     * @return
     */
    @RequestMapping(value="/updateMenu")
    public Map<String, Object> updateMenu(HttpServletRequest request,@ModelAttribute("obj") Menu menu) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();

        roleMenuService.updateMenu(menu);
        resultMap.put("type", "update");
        resultMap.put("code", 1);
        return resultMap;
    }





    /**
     * 删除菜单
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteMenu")
    public Map<String, Object> delete(HttpServletRequest request,Model model) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        roleMenuService.deleteMenuByid(id);

        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }

    /**
     * 批量删除用户
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/batchDeleteMenu")
    public Map<String, Object> batchDeleteMenu(HttpServletRequest request, Model model, @RequestParam("ids[]") int[] ids) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        for (int id : ids) {
            roleMenuService.deleteMenuByid(id);
        }
        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }




}
