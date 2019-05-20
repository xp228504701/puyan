package com.py.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.py.bean.*;
import com.py.service.H5IndexService;
import com.py.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class H5IndexController extends BaseController{
    @Autowired
    private H5IndexService h5IndexService;





    /**
     * 修改信息
     * @param request
     * @param h5indexInfo
     * @return
     */
    @RequestMapping(value = "/updateH5IndexInfo")
    public Map<String, Object> updateH5IndexInfo(HttpServletRequest request, @ModelAttribute("obj") H5indexInfo h5indexInfo){
        //返回map
        Map<String, Object> resultMap = new HashMap<>();

        if (h5indexInfo.getId() == null){
            h5IndexService.insert(h5indexInfo);
        }else{
            h5IndexService.update(h5indexInfo);
        }

        resultMap.put("type", "update");
        resultMap.put("code", 1);
        return resultMap;

    }


    /**
     * 获取Banner
     * @return
     */
    @RequestMapping("/getH5Banner")
    public Map<String,Object> getH5Banner(){
        Map<String,Object> resultMap = new HashMap<>();
        H5indexInfo info = h5IndexService.selectAllIndexInfo();
        List<Banner> adminList = new ArrayList<>();
        if (info.getBannerImg() != null){
            String[] split = info.getBannerImg().split(";");
            for (int i=0;i<split.length;i++){
                Banner b = new Banner();
                b.setImages(split[i]);
                b.setId(i);
                adminList.add(b);
            }
        }

        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("data", adminList);
        return resultMap;

    }




    /**
     * 保存动画编辑
     * @param request
     * @param banner
     * @return
     */
    @RequestMapping("/saveH5Banner")
    public Map<String, Object> saveH5Banner(HttpServletRequest request,@ModelAttribute("obj") Banner banner){

        //返回map
        Map<String, Object> resultMap = new HashMap<>();

        H5indexInfo info = h5IndexService.selectAllIndexInfo();
        if (info.getBannerImg() != null){
            String[] split = info.getBannerImg().split(";");
            if(split.length < 5){
                info.setBannerImg(info.getBannerImg()+banner.getImages()+";");
            }else{
                resultMap.put("type", "add");
                resultMap.put("code", 2);
                return resultMap;
            }
        }
        h5IndexService.update(info);
        resultMap.put("type", "add");
        resultMap.put("code", 1);
        return resultMap;
    }


    /**
     * 修改动画
     * @param request
     * @return
     */
    @RequestMapping(value="/updateH5Banner")
    public Map<String, Object> update(HttpServletRequest request,@ModelAttribute("obj") Banner banner) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        H5indexInfo info = h5IndexService.selectAllIndexInfo();
        if (info.getBannerImg() != null){
            info.setBannerImg(info.getBannerImg().replace(banner.getParam(),banner.getImages()));
        }
        h5IndexService.update(info);
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
    @RequestMapping(value = "/deleteH5Banner")
    public Map<String, Object> delete(HttpServletRequest request, Model model) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        String images = request.getParameter("images");
        H5indexInfo info = h5IndexService.selectAllIndexInfo();
        if (info.getBannerImg() != null){
           info.setBannerImg(info.getBannerImg().replace(images+";",""));
        }
        h5IndexService.update(info);
        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }




    /**********************  新闻中心  **************************/

    /**.
     * 新闻页面信息
     * @return
     */
    @RequestMapping(value = "/getH5newsInfoAll")
    public  Map<String,Object> getH5newsIndexInfo(HttpServletRequest request){

        //获取分页和排序条件
        LayerPage(request);
        //排序插件
        PageHelper.orderBy(orderTable+" "+orderStyle);
        //返回map
        Map<String,Object> resultMap = new HashMap<>();
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<H5newsInfo> newsInfos = h5IndexService.selectAllNewsInfo();
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("count", page.getTotal());
        resultMap.put("data", newsInfos);
        return resultMap;
    }



    /**
     * 新增新闻信息
     * @param request
     * @return
     */
    @RequestMapping(value="saveH5newsInfo")
    public Map<String, Object> saveH5newsInfo(HttpServletRequest request,@ModelAttribute("obj") H5newsInfo h5newsInfo) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        h5IndexService.saveH5news(h5newsInfo);
        resultMap.put("type", "add");
        resultMap.put("code", 1);
        return resultMap;
    }



    /**
     * 修改信息
     * @param request
     * @return
     */
    @RequestMapping(value="/updateH5news")
    public Map<String, Object> updateH5news(HttpServletRequest request,@ModelAttribute("obj") H5newsInfo h5newsInfo) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        h5IndexService.updateH5news(h5newsInfo);
        resultMap.put("type", "update");
        resultMap.put("code", 1);
        return resultMap;
    }


    /**
     * 删除新闻信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteH5news")
    public Map<String, Object> deleteH5news(HttpServletRequest request,Model model) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        h5IndexService.deleteH5newsByid(id);
        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }






    /**********************  帮助中心  **************************/

    /**.
     * 帮助页面信息
     * @return
     */
    @RequestMapping(value = "/getHelpInfoAll")
    public  Map<String,Object> getHelpInfoAll(HttpServletRequest request){

        //获取分页和排序条件
        LayerPage(request);
        //排序插件
        PageHelper.orderBy(orderTable+" "+orderStyle);
        //返回map
        Map<String,Object> resultMap = new HashMap<>();
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<H5helpCenter> newsInfos = h5IndexService.selectAllHelpInfo();
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("count", page.getTotal());
        resultMap.put("data", newsInfos);
        return resultMap;
    }





    /**
     * 新增帮助信息
     * @param request
     * @return
     */
    @RequestMapping(value="saveHelpInfo")
    public Map<String, Object> saveHelpInfo(HttpServletRequest request,@ModelAttribute("obj") H5helpCenter h5helpCenter) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        h5IndexService.saveH5HelpInfo(h5helpCenter);
        resultMap.put("type", "add");
        resultMap.put("code", 1);
        return resultMap;
    }



    /**
     * 修改帮助信息
     * @param request
     * @return
     */
    @RequestMapping(value="/updateHelpInfo")
    public Map<String, Object> updateHelpInfo(HttpServletRequest request,@ModelAttribute("obj") H5helpCenter h5helpCenter) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        h5IndexService.updateH5HelpInfo(h5helpCenter);
        resultMap.put("type", "update");
        resultMap.put("code", 1);
        return resultMap;
    }


    /**
     * 删除帮助信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteHelpInfo")
    public Map<String, Object> deleteHelpInfo(HttpServletRequest request,Model model) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        h5IndexService.deleteH5HelpInfoByid(id);
        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }



    /**********************  友情链接  **************************/

    /**.
     * 友情链接页面信息
     * @return
     */
    @RequestMapping(value = "/getFriendShipAll")
    public  Map<String,Object> getFriendShipAll(HttpServletRequest request){

        //获取分页和排序条件
        LayerPage(request);
        //排序插件
        PageHelper.orderBy(orderTable+" "+orderStyle);
        //返回map
        Map<String,Object> resultMap = new HashMap<>();
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<FriendShip> centerList = h5IndexService.selectFriendShipByExample();
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("count", page.getTotal());
        resultMap.put("data", centerList);
        return resultMap;
    }

    /**
     * 新增友情链接
     * @param request
     * @return
     */
    @RequestMapping(value="saveLinks")
    public Map<String, Object> saveLinks(HttpServletRequest request,@ModelAttribute("obj") FriendShip friendShip) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        h5IndexService.saveFriendLinks(friendShip);
        resultMap.put("type", "add");
        resultMap.put("code", 1);
        return resultMap;
    }



    /**
     * 修改友情链接
     * @param request
     * @return
     */
    @RequestMapping(value="/updateLinks")
    public Map<String, Object> updateLinks(HttpServletRequest request,@ModelAttribute("obj") FriendShip friendShip) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        h5IndexService.updateLinksInfo(friendShip);
        resultMap.put("type", "update");
        resultMap.put("code", 1);
        return resultMap;
    }


    /**
     * 删除友情链接
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteLinks")
    public Map<String, Object> deleteLinks(HttpServletRequest request,Model model) {
        //返回map
        Map<String, Object> resultMap = new HashMap<>();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        h5IndexService.deleteFriendLinksByid(id);
        resultMap.put("type", "delete");
        resultMap.put("code", "success");
        return resultMap;
    }









}
