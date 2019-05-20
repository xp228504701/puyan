package com.py.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.py.bean.MajorClass;
import com.py.bean.SmallClass;
import com.py.service.BusinessTypeService;
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
public class BusinessTypeController extends BaseController{
@Autowired
private BusinessTypeService businessTypeService;





    /************************ 业务大类 ************************/


    /**
     * 获取大类菜单
     * @param request
     * @return
     */
    @RequestMapping("/getMajorAll")
    public Map<String,Object> getMajorAll(HttpServletRequest request) {

        MajorClass mc = new MajorClass();
        //获取分页和排序条件
        LayerPage(request);
        String content = request.getParameter("content");
        if(content != null && !"".equals(content.trim())){
            mc.setName(content);
        }

        //排序插件
        PageHelper.orderBy(orderTable+" "+orderStyle);
        //返回map
        Map<String,Object> resultMap = new HashMap<>();
        Page<?> page = PageHelper.startPage(pageNum, pageSize);

        List<MajorClass> mcList = businessTypeService.selectMajorClassByExample(mc);
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("count", page.getTotal());
        resultMap.put("data", mcList);
        return resultMap;

    }

    /**
     * 根据Id获取单个大类
     * @param classId
     * @return
     */
    @RequestMapping("/getMajorClassById")
    public Map<String,Object> getMajorClassById(@RequestParam("classId")Integer classId){
        MajorClass majorClass = businessTypeService.selectByMajorClassPrimaryKey(classId);
        Map<String,Object> map = new HashMap<>();
        map.put("major",majorClass);
        return map;

    }

    /**
     * 新增大类
     * @param request
     * @return
     */
    @RequestMapping(value="saveMajorClass")
    public Map<String, Object> saveMajorClass(HttpServletRequest request,@ModelAttribute("obj") MajorClass mc) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int m = businessTypeService.selectMajorClassByName(mc.getName());
        if(m > 0) {
            resultMap.put("code", 2);
            resultMap.put("type", "add");
            return resultMap;
        }
        mc.setTime(new Date());
        businessTypeService.saveMajorClass(mc);
        resultMap.put("type", "add");
        resultMap.put("code", 1);
        return resultMap;
    }


    /**
     * 修改大类
     * @param request
     * @return
     */
    @RequestMapping(value="/updateMajorClass")
    public Map<String, Object> updateMajorClass(HttpServletRequest request,@ModelAttribute("obj") MajorClass mc) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();

        businessTypeService.updateMajorClass(mc);
        resultMap.put("type", "update");
        resultMap.put("code", 1);
        return resultMap;
    }


    /**
     * 删除大类
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteMajorClass")
    public Map<String, Object> deleteMajorClass(HttpServletRequest request, Model model) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        businessTypeService.deleteMajorClassByid(id);

        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }

    /**
     * 批量删除大类
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/batchDeleteMajorClass")
    public Map<String, Object> batchDeleteMajorClass(HttpServletRequest request, Model model, @RequestParam("ids[]") int[] ids) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        for (int id : ids) {
            businessTypeService.deleteMajorClassByid(id);
        }
        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }













    /************************ 业务小类 ************************/





    /**
     * 获取小类菜单
     * @param request
     * @return
     */
    @RequestMapping("/getSmallClassAll")
    public Map<String,Object> getSmallClassAll(HttpServletRequest request) {

        SmallClass sc = new SmallClass();
        //获取分页和排序条件
        LayerPage(request);
        String content = request.getParameter("content");
        if(content != null && !"".equals(content.trim())){
            sc.setName(content);
        }

        //排序插件
        PageHelper.orderBy(orderTable+" "+orderStyle);
        //返回map
        Map<String,Object> resultMap = new HashMap<>();
        Page<?> page = PageHelper.startPage(pageNum, pageSize);

        List<SmallClass> mcList = businessTypeService.selectSmallClassByExample(sc);
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("count", page.getTotal());
        resultMap.put("data", mcList);
        return resultMap;

    }

    /**
     * 根据Id获取单个小类
     * @param classId
     * @return
     */
    @RequestMapping("/getSmallClassById")
    public Map<String,Object> getSmallClassById(@RequestParam("classId")Integer classId){
        SmallClass scClass = businessTypeService.selectBySmallClassPrimaryKey(classId);
        Map<String,Object> map = new HashMap<>();
        map.put("small",scClass);
        return map;

    }
    /**
     * 新增小类
     * @param request
     * @return
     */
    @RequestMapping(value="saveSmallClass")
    public Map<String, Object> saveSmallClass(HttpServletRequest request,@ModelAttribute("obj") SmallClass sc) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int m = businessTypeService.selectSmallClassName(sc.getName());
        if(m > 0) {
            resultMap.put("code", 2);
            resultMap.put("type", "add");
            return resultMap;
        }
        sc.setTime(new Date());
        businessTypeService.saveSmallClass(sc);
        resultMap.put("type", "add");
        resultMap.put("code", 1);
        return resultMap;
    }


    /**
     * 修改小类
     * @param request
     * @return
     */
    @RequestMapping(value="/updateSmallClass")
    public Map<String, Object> updateSmallClass(HttpServletRequest request,@ModelAttribute("obj") SmallClass sc) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();

        businessTypeService.updateSmallClass(sc);
        resultMap.put("type", "update");
        resultMap.put("code", 1);
        return resultMap;
    }


    /**
     * 删除小类
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteSmallClass")
    public Map<String, Object> deleteSmallClass(HttpServletRequest request, Model model) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        businessTypeService.deleteSmallClassByid(id);

        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }

    /**
     * 批量删除小类
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/batchDeleteSmallClass")
    public Map<String, Object> batchDeleteSmallClass(HttpServletRequest request, Model model, @RequestParam("ids[]") int[] ids) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        for (int id : ids) {
            businessTypeService.deleteSmallClassByid(id);
        }
        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }

    /**
     * 根据大类id获取小类
     * @return
     */
    @RequestMapping("/getSmallClassByMajorClassId")
    public  Map<String, Object> getSmallClassByMajorClassId(Integer majorId){
        Map<String, Object> resultMap = new HashMap<>();
        List<SmallClass> scList = businessTypeService.selectSmallClassByMajorId(majorId);
        resultMap.put("sc",scList);
        return resultMap;
    }


}
