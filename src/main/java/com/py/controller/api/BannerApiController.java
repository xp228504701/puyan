package com.py.controller.api;

import com.py.bean.Banner;
import com.py.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BannerApiController {
    @Autowired
    private BannerService bannerService;


    /**
     * 获取用户二级banner
     * @return
     */
    @RequestMapping(value = "/getUserTwoBanner",method = RequestMethod.POST)
    public Map<String,Object> getUserTwoBanner(HttpServletRequest request){

        Map<String,Object> resultMap = new HashMap<>();
        Banner list = bannerService.selectUserTwoLevelBanner();
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("data", list);

        return resultMap;
    }


    /**
     * 获取用户一级banner
     * @return
     */
    @RequestMapping(value = "/getUserOneBanner",method = RequestMethod.POST)
    public Map<String,Object> getUserOneBanner(HttpServletRequest request){

        Map<String,Object> resultMap = new HashMap<>();
        List<Banner> bannerList = bannerService.selectUserFirstLevelBanner();
        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("data", bannerList);

        return resultMap;
    }




}
