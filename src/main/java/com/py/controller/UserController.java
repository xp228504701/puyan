package com.py.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.py.bean.User;
import com.py.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     * 获取所有用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserAll")
    public Map<String,Object> getUserAll(HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<>();
        User user = new User();
        LayerPage(request);

        String loginname = request.getParameter("loginname");
        if (!"".equals(loginname) && loginname != null) {
            user.setAccount(loginname);
        }
        //排序插件
        PageHelper.orderBy(orderTable+" "+orderStyle);
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userService.selectByExample(user);

        //返回layui数据
        resultMap.put("code", 0);
        resultMap.put("msg", "查询成功");
        resultMap.put("count", page.getTotal());
        resultMap.put("data", userList);
        return resultMap;
    }

    /**
     * 拉黑/解除拉黑用户
     * @param userId
     * @return
     */
    @RequestMapping("/userBlack")
    public Map<String,Object> userBlack(@RequestParam("userId")Integer userId,@RequestParam("state")Integer state){
        Map<String,Object> resultMap = new HashMap<>();
        User u = userService.selectByPrimaryKey(userId);
        if (u == null){
            resultMap.put("type","error");
            resultMap.put("code",201);
            resultMap.put("msg","用户不存在");
            return resultMap;
        }
        if (u.getStatus() == 0){
            u.setStatus(1);
        }else{
            u.setStatus(0);
        }
        userService.userBlack(u);
        resultMap.put("type", "userBlack");
        resultMap.put("code", "success");
        return resultMap;
    }




}
