package com.py.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.py.bean.*;
import com.py.service.*;
import com.py.util.CommonUtil;
import com.py.util.Msg;
import com.py.util.OfTime;
import com.py.util.OrderNumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/api")
@RestController
public class DemandApiController {
    @Autowired
    private DemandService demandService;
    @Autowired
    private UserNeedService userNeedService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private MasterService masterService;
    @Autowired
    private CertificationService certificationService;





    /**
     * 用户这边的需求
     */
    @RequestMapping(value = "/userDemand",method = RequestMethod.POST)
    public Msg userDemand(HttpServletRequest request){
        String smallClassId = request.getParameter("smallClassId");

        Demand demand = new Demand();
        demand.setSmallClass(Integer.parseInt(smallClassId));
        demand.setType(1);
        Map<Integer, List<Demand>> list = demandService.selectByuserDemandExample(demand);

        return Msg.success().add("map",list);
    }


    /**
     * 用户提交需求订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveUserDemand",method = RequestMethod.POST)
    public Msg saveUserDemand(HttpServletRequest request,String[] tu1,String[] tu2) throws ParseException {


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String smallClassId = request.getParameter("smallClassId");
        if (smallClassId == null || "".equals(smallClassId.trim())){
            return Msg.fail().add("msg","参数有误 -1");
        }
        String userId = request.getParameter("userId");
        if (userId == null || "".equals(userId.trim())){
            return Msg.fail().add("msg","参数有误 -2");
        }
        User user = null;
        try{
            user = userService.selectByPrimaryKey(Integer.parseInt(userId));
        }catch (Exception e){
            e.printStackTrace();
            return Msg.fail().add("msg","获取用户失败");
        }

        String homeTime = request.getParameter("homeTime");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        String phone = request.getParameter("phone");
        String note = request.getParameter("note");

        if (homeTime == null || "".equals(homeTime.trim())){
            return Msg.fail().add("msg","参数有误 -3");
        }
        if (address == null || "".equals(address.trim())){
            return Msg.fail().add("msg","参数有误 -4");
        }
        if (contact == null || "".equals(contact.trim())){
            return Msg.fail().add("msg","参数有误 -5");
        }
        if (phone == null || "".equals(phone.trim())){
            return Msg.fail().add("msg","参数有误 -6");
        }


//        //先把固定的信息存储起来
//        UserNeedInfo uni = new UserNeedInfo();
//        uni.setHomeTime(format.parse(homeTime));
//        uni.setAddress(address);
//        uni.setContact(contact);
//        uni.setPhone(phone);
//        if (note != null || !"".equals(note.trim())){
//            uni.setNote(note);
//        }
//        userNeedService.insertUserNeedInfo(uni);


        //创建订单
        Order o = new Order();
        o.setOrderNo(OrderNumUtil.getOrderNum());//订单号
        o.setCarriage(0.00);//运费
        o.setCashPledge(0.00);//押金
        o.setCreateTime(OfTime.getLongTime());
        o.setDeliveryTime(homeTime);//预约时间
        o.setReceiver(contact);//联系人
        o.setReceiverAddress(address);//联系地址
        o.setReceivingPhone(phone);//联系电话
        o.setUserName(user.getNickName());//用户名称
        o.setUserId(user.getId());//用户id

        if (note != null || !"".equals(note.trim())){
            o.setParam1(note);
        }
        o.setNeedType(Integer.parseInt(smallClassId));

        orderService.insertOrder(o);



        Demand demand = new Demand();
        demand.setSmallClass(Integer.parseInt(smallClassId));
        demand.setType(1);
        List<Demand> list = demandService.selectByExample(demand);

        for (int i = 0; i < list.size(); i++) {
            UserNeed userNeed = new UserNeed();
            if (list.get(i).getLabel() == 1){ //文本框
                String name = request.getParameter(list.get(i).getLabelName());
                if (name != null){
                    userNeed.setLabelName(list.get(i).getLabelName());
                    userNeed.setName(list.get(i).getLabelParam());
                    userNeed.setValue(name);
                    userNeed.setLabelType(list.get(i).getLabel());
                }
            }
            if (list.get(i).getLabel() == 2 || list.get(i).getLabel() == 3){ //单选框 或 多选框
                String name = request.getParameter(list.get(i).getLabelName());
                if (name != null){
                    userNeed.setLabelName(list.get(i).getLabelName());
                    userNeed.setName(list.get(i).getLabelParam());
                    userNeed.setValue(name);
                    userNeed.setParam1(list.get(i).getRemark());
                    userNeed.setLabelType(list.get(i).getLabel());
                }
            }
            if (list.get(i).getLabel() == 4) {
                if (tu1 != null) { //图片4
                    //JSONArray js = new JSONArray(Arrays.asList(tu1));
                    String imgs = "";

                    for (int j = 0; j < tu1.length; j++) {
                        String[] split = tu1[j].split(",");
                        for (int k = 0; k < split.length; k++){
                            if (!split[k].startsWith("data")) {
                                try {
                                    imgs += CommonUtil.imgStr(split[k])+ ";";
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    userNeed.setValue(imgs);
                    userNeed.setLabelName(list.get(i).getLabelName());
                    userNeed.setName(list.get(i).getLabelParam());
                    userNeed.setLabelType(list.get(i).getLabel());
                }else{
                    System.out.println("tu1===null");
                }
            }

            if (list.get(i).getLabel() == 5) {
                if (tu2 != null) { //图片5
                    String imgs = "";
                    for (int j = 0; j < tu2.length; j++) {
                        String[] split = tu2[j].split(",");
                        for (int k = 0; k < split.length; k++){
                            if (!split[k].startsWith("data")) {
                                try {
                                    imgs += CommonUtil.imgStr(split[k])+";";
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    userNeed.setValue(imgs);
                    userNeed.setLabelName(list.get(i).getLabelName());
                    userNeed.setName(list.get(i).getLabelParam());
                    userNeed.setLabelType(list.get(i).getLabel());
                }
            }

            //绑定订单号
            userNeed.setParam2(o.getOrderNo());
            userNeed.setUserId(Integer.parseInt(userId));
            userNeed.setTime(new Date());
            //添加一条信息
            userNeedService.insertUserNeed(userNeed);

        }
        return Msg.success().add("msg","成功");
    }


    /**
     * 师傅提交入驻订单
     * @param request
     * @return
     */
   @RequestMapping(value = "/saveEnteringDemand",method = RequestMethod.POST)
    public Msg saveEnteringDemand(HttpServletRequest request) throws IOException {

       //用于接收对方的jsonString
       StringBuilder jsonString = new StringBuilder();
       BufferedReader reader = request.getReader();
       try {
           String line;
           while ((line = reader.readLine()) != null) {
               jsonString.append(line).append('\n');
           }
       } finally {
           reader.close();
       }
       JSONObject jsonObject = JSONObject.parseObject(jsonString.toString());
       String majorClassId = jsonObject.getString("majorClassId");
        if (majorClassId == null || "".equals(majorClassId.trim())){
            return Msg.fail().add("msg","参数有误 -1");
        }
        String masterId = jsonObject.getString("masterId");
        if (masterId == null || "".equals(masterId.trim())){
            return Msg.fail().add("msg","参数有误 -2");
        }
        Master master = null;
        try{
            master = masterService.selectByPrimaryKey(Integer.parseInt(masterId));
        }catch (Exception e){
            e.printStackTrace();
            return Msg.fail().add("msg","获取师傅信息失败");
        }
        String note = jsonObject.getString("note");

        Demand demand = new Demand();
        demand.setSmallClass(Integer.parseInt(majorClassId));
        demand.setType(2);
        List<Demand> list = demandService.selectByExample(demand);

        for (int i = 0; i < list.size(); i++) {
            Certification userNeed = new Certification();
            if (list.get(i).getLabel() == 1){ //文本框
                String name = jsonObject.getString(list.get(i).getLabelName());
                if (name != null){
                    userNeed.setLabelName(list.get(i).getLabelName());
                    userNeed.setName(list.get(i).getLabelParam());
                    userNeed.setValue(name);
                    userNeed.setLabelType(list.get(i).getLabel());
                }
            }
            if (list.get(i).getLabel() == 2 || list.get(i).getLabel() == 3){ //单选框 或 多选框
                String name = request.getParameter(list.get(i).getLabelName());
                if (name != null){
                    userNeed.setLabelName(list.get(i).getLabelName());
                    userNeed.setName(list.get(i).getLabelParam());
                    userNeed.setValue(name);
                    userNeed.setParam1(list.get(i).getRemark());
                    userNeed.setLabelType(list.get(i).getLabel());
                }
            }
            if (list.get(i).getLabel() == 4) {
                if (jsonObject.getString("img1") != null) { //图片4
                    String img11 = jsonObject.getString("img1");
                    JSONArray img1 = JSONArray.parseArray(img11);
                    String imgs = "";

                    for (int j = 0; j < img1.size(); j++) {

                            if (!img1.get(j).toString().startsWith("data")) {
                                try {
                                    imgs += CommonUtil.imgStr(img1.get(j).toString())+ ";";
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    }
                    userNeed.setValue(imgs);
                    userNeed.setLabelName(list.get(i).getLabelName());
                    userNeed.setName(list.get(i).getLabelParam());
                    userNeed.setLabelType(list.get(i).getLabel());
                }else{
                    System.out.println("tu1===null");
                }
            }

            if (list.get(i).getLabel() == 5) {
                if (jsonObject.getString("img2") != null) { //图片5
                    String img22 = jsonObject.getString("img2");
                    JSONArray img2 = JSONArray.parseArray(img22);
                    String imgs = "";
                    for (int j = 0; j < img2.size(); j++) {
                            if (!img2.get(j).toString().startsWith("data")) {
                                try {
                                    imgs += CommonUtil.imgStr(img2.get(j).toString())+";";
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    }
                    userNeed.setValue(imgs);
                    userNeed.setLabelName(list.get(i).getLabelName());
                    userNeed.setName(list.get(i).getLabelParam());
                    userNeed.setLabelType(list.get(i).getLabel());
                }
            }

            userNeed.setMasterId(Integer.parseInt(masterId));
            userNeed.setTime(new Date());
            //添加一条信息
            certificationService.insertEntering(userNeed);

        }
        return Msg.success().add("msg","成功");
    }




}
