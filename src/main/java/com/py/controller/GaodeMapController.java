package com.py.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import com.alibaba.fastjson.JSONObject;
import com.py.util.HttpUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GaodeMapController {
        private static String API = "https://restapi.amap.com/v3/geocode/geo";
        private static String KEY = "58d81fa085c21e98f88bc6835c448412";


        public static String getLonAndLat(String address){

            String url = "https://restapi.amap.com/v3/geocode/geo";    //请求高德地图的api
            String param = "key="+ KEY +"&address="+address;
            String result = HttpUtils.sendGet(url, param);
            JSONObject object = JSONObject.parseObject(result);
            if((String) object.get("status") == "1" || "1".equals((String)object.get("status"))){
                List<Object> geocodes = (List<Object>) object.get("geocodes");
                JSONObject jsonObject = JSONObject.parseObject( geocodes.get(0).toString());
                return (String) jsonObject.get("location");
            }else{
                return "请求出错";
            }
        }

    public static void main(String[] args) {
        getLonAndLat("广州市番禺区市桥");
    }


}
