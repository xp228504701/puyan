package com.py.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.py.bean.Banner;
import com.py.service.BannerService;
import com.py.util.MD5;
import com.py.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BannerController extends BaseController {
    @Autowired
    private BannerService bannerService;


    /**
     * 获取用户一级banner
     * @return
     */
    @RequestMapping(value = "/getUserBannerById")
    public Map<String,Object> getUserBannerById(HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<>();

        LayerPage(request);
        //排序插件
        PageHelper.orderBy(orderTable+" "+orderStyle);
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<Banner> list = bannerService.selectUserFirstLevelBanner();
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("count", page.getTotal());
        resultMap.put("data", list);

        return resultMap;
    }




    /**
     * 保存动画编辑
     * @param request
     * @param banner
     * @return
     */
    @RequestMapping("/saveBanner")
    public Map<String, Object> saveBanner(HttpServletRequest request,@ModelAttribute("obj") Banner banner){

        //返回map
        Map<String, Object> resultMap = new HashMap<>();

        if(banner.getLevel() == "2" || "2".equals(banner.getLevel())){
            Banner b = bannerService.selectUserTwoLevelBanner();
            if (b != null){
                resultMap.put("type","add");
                resultMap.put("code",101);
                return resultMap;
            }
        }
        banner.setTime(new Date());
        bannerService.saveBanner(banner);
        resultMap.put("type", "add");
        resultMap.put("code", 1);
        return resultMap;


    }


    /**
     * 修改动画
     * @param request
     * @return
     */
    @RequestMapping(value="/updateBanner")
    public Map<String, Object> update(HttpServletRequest request,@ModelAttribute("obj") Banner banner) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();

        banner.setTime(new Date());
        bannerService.update(banner);
        resultMap.put("type", "update");
        resultMap.put("code", 1);
        return resultMap;
    }




    /**
     * 删除动画
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteBanner")
    public Map<String, Object> delete(HttpServletRequest request, Model model) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        bannerService.deleteBannerByid(id);

        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }

    /**
     * 批量删除动画
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "batchDeleteBanner")
    public Map<String, Object> batchDelete(HttpServletRequest request, Model model, @RequestParam("ids[]") int[] ids) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        for (int id : ids) {
            bannerService.deleteBannerByid(id);
        }
        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }





    /**
     * 获取师傅一级banner
     * @return
     */
    @RequestMapping(value = "/getMasterBannerById",method = RequestMethod.POST)
    public Map<String,Object> getMasterBannerById(HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<>();

        LayerPage(request);
        //排序插件
        PageHelper.orderBy(orderTable+" "+orderStyle);
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<Banner> list = bannerService.selectMasterLevelBanner();
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("count", page.getTotal());
        resultMap.put("data", list);

        return resultMap;
    }






}
