package com.py.controller.api;

import com.aliyuncs.exceptions.ClientException;
import com.py.bean.Master;
import com.py.service.MasterService;
import com.py.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api")
public class MasterLoginController {
    @Autowired
    private MasterService masterService;



    /**
     * 获取验证码
     * @param phonenum
     * @param type  1是注册  2是找回密码
     * @param request
     * @return
     */
    @RequestMapping("/getCode")
    public Msg getCode(@RequestParam("phonenum")String phonenum,
                       @RequestParam("type")String type,
                       HttpServletRequest request) {
        if(phonenum == null || "".equals(phonenum)) {
            return Msg.fail().add("msg", "手机号码不能为空");
        }
        String smsTpl = null;
        if(type.equals("1")){
            int m = masterService.selectMasterCount(phonenum);
            if(m > 0){
                return Msg.fail().add("msg", "该用户已经注册，请前往登陆");
            }
            //登录短信模板
            smsTpl = SendMSMUtil.COMMON_TEMPLATE;
        }else{
            smsTpl = SendMSMUtil.COMMON_TEMPLATE_UPDATE;
        }
        try{
            SMSBean smsBean = SendMSMUtil.sendMSM(phonenum, smsTpl, true, null);
            if (smsBean == null) {
                return Msg.fail().add("msg", "短信发送失败");
            } else {
                return Msg.success();
            }
        }catch (ClientException e){
            e.printStackTrace();
            return Msg.fail().add("msg", "短信发送失败");
        }
    }


    /**
     * 师傅注册
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Msg masterLogin(@RequestParam("phonenum")String phonenum
                          ,@RequestParam("password")String password
                          ,@RequestParam("code")String code){

        String msg = CommonUtil.verifyMobileCode(phonenum, code );
        if(msg != null) {
            return Msg.fail().add("msg", msg);
        }
        if (password == null) {
            return Msg.fail().add("msg", "密码不能为空");
        }
        Master master = new Master();
        master.setAccount(phonenum);
        master.setPassword(MD5.string2MD5(password));

        try {
            int i = masterService.register(master);
            if(i <= 0) {
                return Msg.fail();
            }
        } catch (Exception e) {
            return Msg.fail();
        }
        return Msg.success("注册成功");
    }


    /**
     * 登录
     * @param phonenum
     * @param password
     * @return
     */
    @RequestMapping(value="/masterLogin")
    public Msg login(@RequestParam("phonenum")String phonenum,
                     @RequestParam("password")String password){
        if (phonenum == null) {
            return Msg.fail().add("msg", "手机号不能为空");
        }
        if (password == null) {
            return Msg.fail().add("msg", "密码不能为空");
        }
        Msg msg = masterService.login(phonenum,password);
        return msg;
    }

    /**
     * 找回密码
     * @param phonenum
     * @param password
     * @param code
     * @return
     */
    @RequestMapping(value="/retrievePassword",method=RequestMethod.POST)
    public Msg retrievePassword(@RequestParam("phonenum")String phonenum,@RequestParam("password")String password,
                                @RequestParam("code")String code) {
        if (phonenum == null) {
            return Msg.fail().add("msg", "手机号不能为空");
        }
        if (password == null) {
            return Msg.fail().add("msg", "密码不能为空");
        }
        if(code == null) {
            return Msg.fail().add("msg", "请输入验证码");
        }
        String msg = CommonUtil.verifyMobileCode(phonenum, code);
        if(msg != null) {
            return Msg.fail().add("msg", msg);
        }
        try {
            String string = masterService.retrievePassword(phonenum,password);
            if(string != null) {
                return Msg.fail().add("msg", string);
            }
        } catch (Exception e) {
            return Msg.fail().add("msg", "修改失败");
        }

        return Msg.success();
    }

    /**
     * 退出登录
     * @param phonenum
     * @return
     */
    @RequestMapping(value="/dropOut",method=RequestMethod.POST)
    public Msg dropOut(@RequestParam("phonenum")String phonenum) {
        SMSBean smsBean = (SMSBean) CommonUtil.MSG_MAP.get(phonenum);
        if(smsBean != null) {
            Master findMaster= masterService.selectByPrimaryKey(smsBean.getId());
            try {
                findMaster.setToken("");
                findMaster.setOfficeType(0);
                masterService.updateByPrimaryKeySelective(findMaster);
            } catch (Exception e) {
            }
        }else{
            return Msg.fail().add("msg","你还没登录");
        }
        CommonUtil.MSG_MAP.remove(phonenum);
        return Msg.success();
    }




}
