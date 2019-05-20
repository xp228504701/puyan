package com.py.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.py.bean.Admin;
import com.py.service.AdminService;
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
public class AdminController extends BaseController{
    @Autowired
    private AdminService adminService;




    /**
     * 获取用户
     * @param request
     * @return
     */
    @RequestMapping("/getAdmin")
    public Map<String,Object> getAdmin(HttpServletRequest request) {

        //获取登录用户id
        //Admin loginuser=getCurrentUser();
        Admin admin = new Admin();
        //获取分页和排序条件
        LayerPage(request);
        String loginname = request.getParameter("loginname");
        if (!"".equals(loginname) && loginname != null) {
            admin.setAdminAccount(loginname);
        }
        //排序插件
        PageHelper.orderBy("admin_id "+orderStyle);

        //返回map
        Map<String,Object> resultMap = new HashMap<>();
        Page<?> page = PageHelper.startPage(pageNum, pageSize);

        List<Admin> adminList = adminService.selectByExample(admin);
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("count", page.getTotal());
        resultMap.put("data", adminList);
        return resultMap;

    }


    /**
     * 新增用户
     * @param request
     * @return
     */
    @RequestMapping(value="saveAdmin")
    public Map<String, Object> saveAdmin(HttpServletRequest request,@ModelAttribute("obj") Admin admin) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int  countPhone = adminService.selectAccount(admin.getAdminAccount());
        if(countPhone > 0) {
            resultMap.put("code", 2);
            resultMap.put("type", "add");
            return resultMap;
        }
        admin.setAdminCreationTime(new Date());
        admin.setAdminPassWord(MD5.string2MD5(admin.getAdminPassWord()));
        adminService.saveAdmin(admin);
        resultMap.put("type", "add");
        resultMap.put("code", 1);
        return resultMap;
    }

    /**
     * 根据id获取系统用户
     * @param adminId
     * @return
     */
    @RequestMapping("/getAdminById")
    public Admin getAdminById(@RequestParam("id")Integer adminId) {

        Admin admin = new Admin();
        admin.setAdminId(adminId);
        Admin admin2 = adminService.selectByPrimary(admin);
        if (admin2 != null) {
            return admin2;
        }else {
            return null;
        }
    }



    /**
     * 修改用户
     * @param request
     * @return
     */
    @RequestMapping(value="/updateAdmin")
    public Map<String, Object> update(HttpServletRequest request,@ModelAttribute("obj") Admin admin) {
        //返回map
        //返回map
        Map<String, Object> resultMap = new HashMap<>();

        admin.setAdminCreationTime(new Date());
        admin.setAdminPassWord(MD5.string2MD5(admin.getAdminPassWord()));
        adminService.update(admin);
        resultMap.put("type", "update");
        resultMap.put("code", 1);
        return resultMap;
    }





    /**
     * 删除用户
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteAdmin")
    public Map<String, Object> delete(HttpServletRequest request,Model model) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        adminService.deleteAdminByid(id);

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
    @RequestMapping(value = "batchDelete")
    public Map<String, Object> batchDelete(HttpServletRequest request, Model model, @RequestParam("ids[]") int[] ids) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        for (int id : ids) {
            adminService.deleteAdminByid(id);
        }
        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }



}
