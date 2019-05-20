package com.py.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.py.bean.User;
import com.py.service.UserService;
import com.py.util.HttpUtils;
import com.py.util.Msg;
import com.py.util.weChart.WXConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserApiController {
    @Autowired
    private UserService userService;


    /**
     * 微信授权登录
     * @param code
     * @return
     */
    @RequestMapping(value = "/wxLogin")
    public Map<String,Object> wxLogin(@RequestParam("loginCode")String code){

        String url = "https://api.weixin.qq.com/sns/jscode2session";    //微信登录授权URL
        String param = "appid="+ WXConfig.APP_ID +"&secret="+WXConfig.App_Secret+"&js_code="+code+"&grant_type=authorization_code";
        String result = HttpUtils.sendGet(url, param);
        JSONObject string = JSONObject.parseObject(result);
        Map<String,Object> map = new HashMap<>();
        if (string.get("errcode") != null){
            map.put("code",200);
            map.put("msg","出错了");
            map.put("errcode",result);
            return map;
        }


        //if(string.get("openid") == null){
            //获取access_token
//            String token = "https://api.weixin.qq.com/sns/oauth2/access_token";
//            String token_param = "appid="+ WXConfig.APP_ID +"&secret="+WXConfig.App_Secret+"&code="+code+"&grant_type=authorization_code";
//            String wx_token = HttpUtils.sendGet(token, token_param);
//            JSONObject json = JSONObject.parseObject(wx_token);
//
//            if (json.get("errcode") != null){
//                map.put("code",201);
//                map.put("msg","出错了");
//                map.put("errcode",wx_token);
//                return map;
//            }
//
//            //拿到access_token
//            String access_token = (String) json.get("access_token");
//            String refresh_token = (String) json.get("refresh_token");
//            //保存token
//            MyCacheUtil.getInstance().addCacheData((String) string.get("openid"),access_token);
//            MyCacheUtil.getInstance().addCacheData((String) string.get("openid")+"refresh_token",refresh_token);
       /* }else{

            //获取access_token
            String cacheData = (String) MyCacheUtil.getInstance().getCacheData(string.get("openid") + "refresh_token");
            String token = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
            String refresh_token = "appid="+ WXConfig.APP_ID +"&grant_type=refresh_token&refresh_token="+cacheData;
            String wx_token = HttpUtils.sendGet(token, refresh_token);
            JSONObject refresh_t = JSONObject.parseObject(wx_token);

            if (refresh_t.get("errcode") != null){
                map.put("code",202);
                map.put("msg","出错了");
                map.put("errcode",wx_token);
                return map;
            }

        }*/



        map.put("openid",string.get("openid"));
        map.put("code",100);
        map.put("msg","成功");
        return map;
    }


    /**
     * 保存用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveUserInfo",method = RequestMethod.POST)
    public Msg saveUserInfo(HttpServletRequest request){

        String rawData = request.getParameter("rawData");
        String openId = request.getParameter("openId");
        JSONObject raw = JSONObject.parseObject(rawData);
        String nickName = (String)raw.get("nickName");


        User user = userService.selectByAccount(openId);
        if(user == null){
            User u = new User();
            u.setAccount(openId);
            u.setNickName(nickName);
            u.setStatus(1);
            u.setCreateTime(new Date());
            userService.insert(u);
            return Msg.success().add("userId",u.getId());
        }else{
            user.setNickName(nickName);
            user.setStatus(1);
            userService.update(user);
            return Msg.success().add("userId",user.getId());
        }

    }

    /**
     * 通过openId获取userId
     * @param openId
     * @return
     */

    @RequestMapping(value = "/getUserIdByOpenId",method = RequestMethod.POST)
    public Msg sendUserWx(String openId){

        if (openId != null){
            User user = userService.selectUserIdByOpenId(openId);
            return Msg.success().add("userId",user.getId());
        }else{
           return Msg.fail().add("msg","参数有误");
        }
    }






}
