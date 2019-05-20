package com.py.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.py.bean.Demand;
import com.py.service.DemandService;
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
public class DemandController extends BaseController {
    @Autowired
    private DemandService demandService;






    /**
     * 
     * 获取需求信息
     * @param request
     * @return
     */
    @RequestMapping("/getDemandAll")
    public Map<String,Object> getDemandAll(HttpServletRequest request) {

        Demand demand = new Demand();
        //获取分页和排序条件
        LayerPage(request);
        String content = request.getParameter("content");
        if(content != null && !"".equals(content.trim())){
            demand.setName(content);
        }

        //排序插件
        PageHelper.orderBy(orderTable+" "+orderStyle);
        //返回map
        Map<String,Object> resultMap = new HashMap<>();
        Page<?> page = PageHelper.startPage(pageNum, pageSize);

        List<Demand> mcList = demandService.selectByExample(demand);
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("count", page.getTotal());
        resultMap.put("data", mcList);
        return resultMap;

    }


    /**
     * 新增需求信息
     * @param request
     * @return
     */
    @RequestMapping(value="saveDemand")
    public Map<String, Object> saveDemand(HttpServletRequest request,@ModelAttribute("obj") Demand sc) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int m = demandService.selectDemandName(sc.getLabelName());
        if(m > 0) {
            resultMap.put("code", 2);
            resultMap.put("type", "add");
            return resultMap;
        }
        int d = demandService.selectDemandLabelId(sc.getLabelId());
        if(d > 0) {
            resultMap.put("code", 3);
            resultMap.put("type", "add");
            return resultMap;
        }

        if (sc.getType() == 2){
           sc.setSmallClass(Integer.parseInt(request.getParameter("majorClass")));
        }
        sc.setTime(new Date());
        demandService.saveDemand(sc);
        resultMap.put("type", "add");
        resultMap.put("code", 1);
        return resultMap;
    }


    /**
     * 修改需求
     * @param request
     * @return
     */
    @RequestMapping(value="/updateDemand")
    public Map<String, Object> updateDemand(HttpServletRequest request,@ModelAttribute("obj") Demand sc) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();

        demandService.updateDemand(sc);
        resultMap.put("type", "update");
        resultMap.put("code", 1);
        return resultMap;
    }


    /**
     * 删除需求
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteDemand")
    public Map<String, Object> deleteDemand(HttpServletRequest request, Model model) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        demandService.deleteDemandByid(id);

        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }

    /**
     * 批量删除需求
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/batchDeleteDemand")
    public Map<String, Object> batchDeleteDemand(HttpServletRequest request, Model model, @RequestParam("ids[]") int[] ids) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        for (int id : ids) {
            demandService.deleteDemandByid(id);
        }
        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }




}
