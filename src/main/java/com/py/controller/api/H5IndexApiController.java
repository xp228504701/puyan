package com.py.controller.api;

import com.py.bean.FriendShip;
import com.py.bean.H5helpCenter;
import com.py.bean.H5indexInfo;
import com.py.bean.H5newsInfo;
import com.py.service.H5IndexService;
import com.py.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/api")
@RestController
public class H5IndexApiController {
    @Autowired
    private H5IndexService h5IndexService;

    /**
     * 首页logo信息
     * @return
     */
    @RequestMapping(value = "/getH5IndexLogo",method = RequestMethod.POST)
    public Map<String,Object> getH5IndexLogo(){
        H5indexInfo info = h5IndexService.selectAllIndexInfo();
        Map<String,Object> map = new HashMap<>();
        if (info == null){
            map.put("logo","");
        }else{
            map.put("logo",info.getLogoImg());
        }
        return map;
    }


    /**
     * 首页banner信息
     * @return
     */
    @RequestMapping(value = "/getH5IndexBanner",method = RequestMethod.POST)
    public List<String> getH5IndexBanner(){
        H5indexInfo info = h5IndexService.selectAllIndexInfo();
        List<String> list = new ArrayList<>();
        if (info != null) {
            String[] split = info.getBannerImg().split(";");
            for (int i = 0; i < split.length; i++) {
                list.add(split[i]);
            }
        }
        return list;
    }


    /**
     * 首页下单图片
     * @return
     */
    @RequestMapping(value = "/getH5IndexProcess",method = RequestMethod.POST)
    public Map<String,Object> getH5IndexProcess(){
        H5indexInfo info = h5IndexService.selectAllIndexInfo();
        Map<String,Object> map = new HashMap<>();
        if (info != null){
            map.put("process",info.getProcessImg());
        }else{
            map.put("process","");
        }
        return map;
    }

    /**
     * 获取公司信息
     * @return
     */
    @RequestMapping(value = "/getH5IndexCompany",method = RequestMethod.POST)
    public Map<String,Object> getH5IndexCompany(){
        H5indexInfo info = h5IndexService.selectAllIndexInfo();

        Map<String,Object> map = new HashMap<>();
        if (info != null){
            map.put("companyName",info.getCompanyName());
            map.put("companyAddress",info.getCompanyAddress());
            map.put("companyPhone",info.getCompanyPhone());
            map.put("companyPostCode",info.getCompanyPostcode());
            map.put("companyFax",info.getCompanyFax());

            map.put("icp",info.getParam1());        //备案编码
            map.put("icp_link",info.getParam2());   //链接信息
        }
        return map;
    }


    /**.
     * 新闻页面信息
     * @return
     */
    @RequestMapping(value = "/getH5newsIndexInfo",method = RequestMethod.POST)
    public Msg getH5newsIndexInfo(){
        List<H5newsInfo> newsInfos = h5IndexService.selectAllNewsInfo();
        return Msg.success().add("news",newsInfos);
    }


    /**
     * 获取帮助所有的问题
     * @return
     */
    @RequestMapping(value = "/getH5HelpInfo",method = RequestMethod.POST)
    public Msg getH5HelpInfo(){
        List<H5helpCenter> centerList = h5IndexService.selectAllHelpInfo();
        return Msg.success().add("help",centerList);
    }

    /**
     * 根据问题id寻找答案
     * @return
     */
    @RequestMapping(value = "/getH5HelpInfoById",method = RequestMethod.POST)
    public Msg getH5HelpInfo(HttpServletRequest request){
        String helpId = request.getParameter("helpId");
        H5helpCenter centerList = h5IndexService.selectH5HelpByPrimaryKey(Integer.parseInt(helpId));
        return Msg.success().add("help",centerList);
    }


    /**
     * 获得所有的友情链接
     * @return
     */
    @RequestMapping(value = "/getFriendShip",method = RequestMethod.POST)
    public Msg getFriendShipAll(HttpServletRequest request){
        List<FriendShip> centerList = h5IndexService.selectFriendShipByExample();
        return Msg.success().add("friendShip",centerList);
    }


    /**
     * 获取中间大的引导图
     * @return
     */
    @RequestMapping(value = "/getCenterImg",method = RequestMethod.POST)
    public Map<String,Object>  getCenterImg(HttpServletRequest request){
        H5indexInfo info = h5IndexService.selectAllIndexInfo();
        Map<String,Object> map = new HashMap<>();
        if (info != null){
            map.put("centerImg",info.getCenterImg());
        }else{
            map.put("centerImg","");
        }
        return map;
    }







}
