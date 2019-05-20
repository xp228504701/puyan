package com.py.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.py.bean.Admin;
import com.py.bean.Master;
import com.py.service.MasterService;
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
public class MasterController extends  BaseController{
    @Autowired
    private MasterService masterService;



    /**
     * 获取师傅
     * @param request
     * @return
     */
    @RequestMapping("/getMasterAll")
    public Map<String,Object> getMasterAll(HttpServletRequest request) {

        //获取登录用户id
        //Admin loginuser=getCurrentUser();
        Master master = new Master();
        //获取分页和排序条件
        LayerPage(request);
        String loginname = request.getParameter("loginname");
        if (!"".equals(loginname) && loginname != null) {
            master.setAccount(loginname);
        }
        //排序插件
        PageHelper.orderBy(orderTable+" "+orderStyle);

        //返回map
        Map<String,Object> resultMap = new HashMap<>();
        Page<?> page = PageHelper.startPage(pageNum, pageSize);

        List<Master> adminList = masterService.selectByExample(master);
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("count", page.getTotal());
        resultMap.put("data", adminList);
        return resultMap;

    }



    /**
     * 根据id获取师傅信息
     * @param masterId
     * @return
     */

    @RequestMapping("/getMasterById")
    public Master getMasterById(@RequestParam("id")Integer masterId) {

        Master master = new Master();
        master.setId(masterId);
        Master m = masterService.selectByPrimary(master);
        if (m != null) {
            return m;
        }else {
            return null;
        }
    }




    /**
     * 修改师傅
     * @param request
     * @return
     */

    @RequestMapping(value="/updateMaster")
    public Map<String, Object> update(HttpServletRequest request,@ModelAttribute("obj") Master master) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();

        master.setTime(new Date());
       // admin.setAdminPassWord(MD5.string2MD5(admin.getAdminPassWord()));
        masterService.update(master);
        resultMap.put("type", "update");
        resultMap.put("code", 1);
        return resultMap;
    }





    /**
     * 删除师傅
     * @param request
     * @param model
     * @return
     */

    @RequestMapping(value = "/deleteMaster")
    public Map<String, Object> delete(HttpServletRequest request, Model model) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        masterService.deleteMasterByid(id);

        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }

    /**
     * 批量删除师傅
     * @param request
     * @param model
     * @return
     */

    @RequestMapping(value = "batchDeleteMaster")
    public Map<String, Object> batchDelete(HttpServletRequest request, Model model, @RequestParam("ids[]") int[] ids) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        for (int id : ids) {
            masterService.deleteMasterByid(id);
        }
        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }




}
