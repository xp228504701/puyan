package com.py.controller.api;

import com.py.bean.MajorClass;
import com.py.bean.SmallClass;
import com.py.service.BusinessTypeService;
import com.py.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BusinessTypeApiController {
    @Autowired
    private BusinessTypeService businessTypeService;


    /**
     *
     * @title 获取所有的大类名称信息
     * @return
     */
    @RequestMapping(value = "/getMajorClass",method = RequestMethod.POST)
    public Msg getMajorClass(){
        List<MajorClass> majorClassList = businessTypeService.selectMajorClassByExample(new MajorClass());
        return Msg.success().add("list",majorClassList);
    }


    /**
     *
     * @title 根据大类id寻找小类信息
     * @return
     */
    @RequestMapping(value = "/getSmallClassByMajorClassId",method = RequestMethod.POST)
    public  Msg getSmallClassByMajorClassId(@RequestParam("majorId")Integer majorId){
        if (majorId == null || majorId == 0){
            return Msg.fail().add("msg","参数出错!");
        }
        List<SmallClass> smallClassList = businessTypeService.selectSmallClassByMajorId(majorId);
        return Msg.success().add("list",smallClassList);
    }


    /**
     *
     * @title 搜索小类名称
     * @return
     */
    @RequestMapping(value = "/searchSmallClass",method = RequestMethod.POST)
    public Msg searchSmallClass(@RequestParam("smallClassName")String smallClassName){
        List<MajorClass> scList = null;
        if ("".equals(smallClassName.trim()) || smallClassName == null){
            scList = businessTypeService.selectMajorClassByExample(new MajorClass());
        }else{
            scList = businessTypeService.selectSmallClassByName(smallClassName);
        }
        return Msg.success().add("list",scList);
    }

    /** 未完成
     * 获取小类的banner广告图
     * @return
     */
    @RequestMapping(value = "/getClassBanner",method = RequestMethod.POST)
    public Msg getClassBanner(){


        return Msg.success();
    }





}
