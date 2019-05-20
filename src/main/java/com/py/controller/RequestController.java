package com.py.controller;

import com.py.bean.*;
import com.py.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RequestController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private BusinessTypeService businessTypeService;
    @Autowired
    private DemandService demandService;
    @Autowired
    private UserService userService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private H5IndexService h5IndexService;






   /**************************   H5页面  **********************************/

    /**
     * 首页信息编辑
     * @return
     */
    @RequestMapping(value = "/h5IndexInfo")
    public String h5IndexInfo(Model model){
        H5indexInfo indexInfo = h5IndexService.selectAllIndexInfo();
        if (indexInfo != null){
            model.addAttribute("info",indexInfo);
        }
        return "H5/index/h5-index";
    }


    /**
     * 帮助中心
     * @return
     */
    @RequestMapping(value = "/h5HelpCenterInfo")
    public String h5HelpCenterInfo(){
        return "H5/help/h5-help";
    }
    @RequestMapping("/h5HelpCenterForm")
    public String h5HelpCenterForm (HttpServletRequest request, Model model){
        int id = 0;
        try {
            id= Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        if(id > 0) {
            H5helpCenter info = h5IndexService.selectH5HelpByPrimaryKey(id);
            model.addAttribute("Surl","/updateHelpInfo");
            model.addAttribute("info", info);
        }else{
            model.addAttribute("Surl","/saveHelpInfo");
        }
        return "H5/help/doForm";
    }

    /**
     * 新闻中心
     * @return
     */
    @RequestMapping(value = "/h5NewsInfo")
    public String h5NewsInfo(){
        return "H5/news/h5-news";
    }
    @RequestMapping("/h5NewsInfoForm")
    public String h5NewsInfoForm (HttpServletRequest request, Model model){
        int id = 0;
        try {
            id= Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        if(id > 0) {
            H5newsInfo info = h5IndexService.selectH5NewsByPrimaryKey(id);
            model.addAttribute("Surl","/updateH5news");
            model.addAttribute("info", info);
        }else{
            model.addAttribute("Surl","/saveH5newsInfo");
        }

        return "H5/news/doForm";
    }

    /**
     * 友情链接
     * @return
     */
    @RequestMapping("/linksList")
    public String linksList (){
        return "H5/friend-ship/friend-ship";
    }
    @RequestMapping("/linksForm")
    public String linksForm (HttpServletRequest request, Model model){
        int id = 0;
        try {
            id= Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        if(id > 0) {
            FriendShip info = h5IndexService.selectLinkByPrimaryKey(id);
            model.addAttribute("Surl","/updateLinks");
            model.addAttribute("info", info);
        }else{
            model.addAttribute("Surl","/saveLinks");
        }

        return "H5/friend-ship/doForm";
    }





    /**
     * H5banner信息
     * @return
     */
    @RequestMapping(value = "/h5ContactUsInfo")
    public String h5ContactUsInfo(){
        return "H5/contact-us/contact-us";
    }
    @RequestMapping("/h5BannerForm")
    public String h5BannerForm (HttpServletRequest request, Model model){
        int id = -1;
        try {
            id= Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = -1;
        }
        if(id >= 0) {
            String images = "";
            H5indexInfo info = h5IndexService.selectAllIndexInfo();
            Banner b = new Banner();
            if (info.getBannerImg() != null){
                String[] split = info.getBannerImg().split(";");
                b.setImages(split[id]);
                b.setId(id);
            }
            model.addAttribute("Surl","/updateH5Banner");
            model.addAttribute("images", b);
        }else{
            model.addAttribute("Surl","/saveH5Banner");
        }

        return "H5/contact-us/doForm";
    }






    /*************************  后台  ***********************************/

    @RequestMapping(value = "/hello")
    public String hello(Model model){
        return "page/login";
    }
    @RequestMapping(value = "/jumpLogin")
    public String jumpLogin(Model model){
        return "page/login";
    }
    @RequestMapping(value = "/jumpIndex")
    public String jumpIndex(Model model){
        List<Menu> menus = roleMenuService.selectFatherName();
        List<Menu> sonName = roleMenuService.selectSonName();
        model.addAttribute("menus",menus);
        model.addAttribute("sonName",sonName);
        return "page/index";
    }

    @RequestMapping("/menuList")
    public String menuList(){
        return "menu/menu";
    }
    @RequestMapping("/menuForm")
    public String menuForm(HttpServletRequest request, Model model){

        int id = 0;
        try {
            id= Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        if(id > 0) {
            Menu menu = roleMenuService.selectMenuByPrimaryId(id);
            model.addAttribute("Surl","/updateMenu");
            model.addAttribute("menu", menu);
        }else{
            model.addAttribute("menu", null);
            model.addAttribute("Surl","/saveMenu");
        }

        List<Menu> fatherName = roleMenuService.selectFatherName();
        model.addAttribute("fatherName",fatherName);
        return "menu/doForm";
    }

    @RequestMapping("/adminList")
    public String adminList(){
        return "admin/admin";
    }
    @RequestMapping("/adminForm")
    public String adminForm(HttpServletRequest request, Model model){

        int id = 0;
        try {
            id= Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        if(id > 0) {
            Admin admin = adminService.selectByPrimaryKey(id);
            model.addAttribute("Surl","/updateAdmin");
            model.addAttribute("admin", admin);
        }else{
            model.addAttribute("Surl","/saveAdmin");
        }

        return "admin/doForm";
    }


    @RequestMapping("/majorList")
    public String majorList (){
        return "business-class/major-class/major";
    }

    @RequestMapping("/majorForm")
    public String majorForm (HttpServletRequest request, Model model){
        int id = 0;
        try {
            id= Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        if(id > 0) {
            MajorClass majorClass = businessTypeService.selectByMajorClassPrimaryKey(id);
            model.addAttribute("Surl","/updateMajorClass");
            model.addAttribute("mc", majorClass);
        }else{
            model.addAttribute("Surl","/saveMajorClass");
        }

        return "business-class/major-class/doForm";
    }

    @RequestMapping("/smallList")
    public String smallList (){
        return "business-class/small-class/small";
    }
    @RequestMapping("/smallForm")
    public String smallForm (HttpServletRequest request, Model model){
        int id = 0;
        try {
            id= Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        List<MajorClass> majorClassList = businessTypeService.selectMajorClassAllName();
        if(id > 0) {
            SmallClass sc = businessTypeService.selectBySmallClassPrimaryKey(id);
            model.addAttribute("Surl","/updateSmallClass");
            model.addAttribute("sc", sc);
            model.addAttribute("mc", majorClassList);

        }else{
            model.addAttribute("mc", majorClassList);
            model.addAttribute("Surl","/saveSmallClass");
        }
        return "business-class/small-class/doForm";
    }

    //佣金比例设置
    @RequestMapping("/brokerage")
    public String brokerage (){
        return "business-class/brokerage/brokerage";
    }
    //用户需求设置
    @RequestMapping("/demandList")
    public String demandList (){
        return "business-class/demandList/demand-list";
    }
    @RequestMapping("/demandForm")
    public String demandForm (HttpServletRequest request, Model model){
        int id = 0;
        try {
            id= Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        List<MajorClass> majorClassList = businessTypeService.selectMajorClassAllName();
        if(id > 0) {
            Demand demand = demandService.selectByPrimaryKey(id);
            model.addAttribute("Surl","/updateDemand");
            //model.addAttribute("sc", sc);
            model.addAttribute("mc", majorClassList);
            model.addAttribute("dm",demand);

        }else{
            model.addAttribute("mc", majorClassList);
            model.addAttribute("Surl","/saveDemand");
        }
        return "business-class/demandList/doForm";
    }

    //用户管理
    @RequestMapping("/userList")
    public String userList (){
        return "operation/user/user";
    }

    //师傅管理
    @RequestMapping("/masterList")
    public String masterList (){
        return "operation/master/master";
    }
    //新闻管理
    /*@RequestMapping("/newsList")
    public String newsList (){
        return "operation/news/news";
    }*/
    //首页动画设置
    @RequestMapping("/bannerIndex")
    public String bannerIndex (){
        return "operation/banner-index/banner-index";
    }
    //动画的编辑
    @RequestMapping("/bannerForm")
    public String bannerForm (HttpServletRequest request, Model model){
        int id = 0;
        try {
            id= Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        if(id > 0) {
            Banner banner = bannerService.selectByPrimaryKey(id);
            model.addAttribute("Surl","/updateBanner");
            //model.addAttribute("sc", sc);
            model.addAttribute("banner",banner);

        }else{
            model.addAttribute("Surl","/saveBanner");
        }
        return "operation/banner-index/doForm";
    }

    //二级菜单动画设置
    @RequestMapping("/bannerNext")
    public String bannerNext (){
        return "operation/banner-next/banner-next";
    }
    //备案编辑
    @RequestMapping("/filingList")
    public String filingList (){
        return "operation/filing/filing";
    }







}
