package com.py.controller;

import com.py.bean.Admin;
import com.py.service.AdminService;
import com.py.util.MD5;
import com.py.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;

@RestController
public class AdminLoginController {
    @Autowired
    private AdminService adminService;


    /**
     * 登陆
     * @param adminAccount
     * @param adminPassWord
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginAdmin",method = RequestMethod.POST)
    public Msg Login(@RequestParam("account")String adminAccount, @RequestParam("password")String adminPassWord,
                     HttpServletRequest request){

        Admin admin = new Admin();
        if (!adminAccount.equals("") && !adminAccount.equals(" ") && !adminAccount.equals(null)){
            admin.setAdminAccount(adminAccount);
        }else{
            return Msg.fail().add("msg", "账号不能为空！");
        }
        if (!adminPassWord.equals("") && !adminPassWord.equals(" ") && !adminPassWord.equals(null)){
            String pwd = MD5.string2MD5(adminPassWord);
            admin.setAdminPassWord(pwd);
        }else{
            return Msg.fail().add("msg", "密码不能为空！");
        }
        Admin a = adminService.selectByPrimary(admin);

        if (a != null) {

            SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String time = fomat.format(a.getAdminCreationTime());
            HttpSession session = request.getSession();
            session.setAttribute("adminId", a.getAdminId());
            session.setAttribute("adminAccount", a.getAdminAccount());
            session.setAttribute("adminPassword", a.getAdminPassWord());
            session.setAttribute("adminRealname", a.getAdminFullName());
            session.setAttribute("adminCreateTime", time);
            return Msg.success().add("msg", "登录成功");
        }else {
            return Msg.fail().add("msg", "登陆失败，账号或密码不不正确");
        }

    }


    /**
     * 退出
     * @throws IOException
     */
    @RequestMapping("/LoginOut")
    public Msg LoginOut(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();

        session.invalidate();
        response.sendRedirect("/login");
        return Msg.success();
    }


}
