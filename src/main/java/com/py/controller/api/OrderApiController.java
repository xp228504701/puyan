package com.py.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.py.bean.*;
import com.py.service.BusinessTypeService;
import com.py.service.MasterService;
import com.py.service.OrderService;
import com.py.service.UserNeedService;
import com.py.util.Msg;
import com.py.util.OfTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderApiController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserNeedService userNeedService;
    @Autowired
    private MasterService masterService;



    /**********************  小程序  *************************/

    /**
     * 根据状态获取订单信息(简单信息)
     * @param request
     * @return
     */
    @RequestMapping(value = "/getOrderByStatus",method = RequestMethod.POST)
    public Msg getOrderByStatus (HttpServletRequest request){

        String state = request.getParameter("status");
        String userId = request.getParameter("userId");
        if ((state == null || "".equals(state)) || (userId == null || "".equals(userId))){
            return Msg.fail().add("msg","参数错误");
        }
        //订单的简介
        List<OrderSynopsis> synopsis = orderService.selectSynopsisByStatus(state,userId);
        return Msg.success().add("list",synopsis);
    }



    /**
     * 根据订单编号获取订单信息(详细信息)
     * @param request
     * @return
     */
    @RequestMapping(value = "/getOrderByOrderNum",method = RequestMethod.POST)
    public Msg getOrderByOrderNum (HttpServletRequest request){

        String orderNo = request.getParameter("orderNo");
        if (orderNo == null || "".equals(orderNo)){
            return Msg.fail().add("msg","参数错误");
        }
        //订单信息
        Order order = orderService.selectByPrimary(orderNo);
        if (order == null){
            return Msg.fail().add("msg","该订单编号不存在");
        }
        //根据订单编号查找需求
        List<UserNeed> needs = userNeedService.selectByOrderNo(orderNo);
        //封装订单Bean
        OrderDetail od = new OrderDetail();
        od.setCreateTime(order.getCreateTime());
        od.setHomeTime(order.getDeliveryTime());
        od.setOrderId(order.getOrderId());
        od.setReceiver(order.getReceiver());
        od.setReceiverAddress(order.getReceiverAddress());
        od.setReceiverPhone(order.getReceivingPhone());
        od.setOrderNo(order.getOrderNo());
        od.setOrderNote(order.getParam1());
        od.setUserNeeds(needs);

        return Msg.success().add("order",od);
    }



    /**
     * 取消订单
     * @param orderNo 订单编号
     * @return
     */
    @RequestMapping(value = "/cancelOrderByOrderId",method = RequestMethod.POST)
    public Msg cancelOrderByOrderId (@RequestParam("orderNo")String orderNo){

        Order order = orderService.selectByPrimary(orderNo);
        if (order == null){
            return Msg.fail().add("msg","此订单不存在");
        }
        order.setState(5);
        int i = orderService.updateOrderState(order);
        if (i > 0){
            return Msg.success();
        }else{
            return Msg.fail().add("msg","取消失败");
        }
    }


    /**
     * 根据订单号查找报价详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMasterQuoteByStatus",method = RequestMethod.POST)
    public Msg getMasterQuoteByStatus(HttpServletRequest request){
        String orderNo = request.getParameter("orderNo");    //订单号
        if (orderNo == null){
            return Msg.fail().add("msg","参数错误");
        }
        MasterQuote mq = new MasterQuote();
        mq.setStatus(1);
        mq.setOrderNum(orderNo);
        List<QuoteSynopsis> synopsisList = orderService.selectQuoteByOrderNo(mq);
        return Msg.success().add("list",synopsisList);
    }

    /**
     * 接受报价
     * @param request
     * @return
     */
    @RequestMapping("/acceptQuote")
    public Msg acceptQuote(HttpServletRequest request){
        String masterQuoteId = request.getParameter("masterQuoteId");    //师傅报价id
        String orderNo = request.getParameter("orderNo");    //订单号

        if (orderNo == null){
            return Msg.fail().add("msg","参数错误 -1");
        }
        if (masterQuoteId == null){
            return Msg.fail().add("msg","参数错误 -2");
        }
        String status = orderService.acceptQuote(orderNo, masterQuoteId);
        return Msg.success().add("string",status);
    }


    /**
     * 订单支付(在线支付--微信)
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderPayForWx",method = RequestMethod.POST)
    public Msg orderPayForWx (HttpServletRequest request){

        return Msg.success();
    }

    /**
     * 订单支付(在线支付--支付宝)
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderPayForZfb",method = RequestMethod.POST)
    public Msg orderPayForZfb (HttpServletRequest request){



        return Msg.success();
    }

    /**
     * 订单支付(线下支付)
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateOrderPayStatus",method = RequestMethod.POST)
    public Msg updateOrderPayStatus (HttpServletRequest request){

        String orderNo = request.getParameter("orderNo");    //订单号
        Order order = orderService.selectByPrimary(orderNo);
        if (order == null){
            return Msg.fail().add("msg","此订单不存在");
        }
        order.setPayStatus(3);
        order.setState(2);
        orderService.updateOrderState(order);

        return Msg.success();
    }

    /**
     * 发送订单通知,通知师傅上门
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendOrderNotice",method = RequestMethod.POST)
    public Msg sendOrderNotice (HttpServletRequest request){

        //需要写通知

        return Msg.success();
    }



    /**
     * 评价订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/evaluationOrderById",method = RequestMethod.POST)
    public Msg evaluationOrderById (HttpServletRequest request){



        return Msg.success();
    }





    /**********************  师傅App端  *************************/

    /**
     *  根据大类id查找订单
     * @param request 关联大类id
     * @return
     */
    @RequestMapping(value = "/getOrderByStatusForMaster",method = RequestMethod.POST)
    public Msg getOrderByStatusForMaster(HttpServletRequest request) throws IOException {

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

        String masterId = jsonObject.getString("masterId");
        Master m = masterService.selectByPrimaryKey(Integer.parseInt(masterId));
        if (m == null){
            return Msg.fail().add("msg","没有此用户");
        }
        List<MasterCertification> certificationList = masterService.selectCertificationByMasterId(m.getId());
        if (certificationList.size() == 0 || certificationList == null){
            return Msg.fail().add("msg","请先入驻");
        }
        Integer[] ids = new Integer[certificationList.size()];
        for (int i=0;i<certificationList.size();i++){
            ids[i] = certificationList.get(i).getMajorClassId();
        }
        List<OrderSynopsisMaster> synopsisMasterList = orderService.selectOrderByStatusAndMajorClassForMaster(ids);
        return Msg.success().add("list",synopsisMasterList);
    }


    /**
     * 根据订单编号获取订单信息(详细信息)
     * @param request
     * @return
     */
    @RequestMapping(value = "/getOrderByOrderNumForMaster",method = RequestMethod.POST)
    public Msg getOrderByOrderNumForMaster (HttpServletRequest request) throws IOException {

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

        String orderNo = jsonObject.getString("orderNo");
        if (orderNo == null || "".equals(orderNo)){
            return Msg.fail().add("msg","参数错误");
        }
        //订单信息
        Order order = orderService.selectByPrimary(orderNo);
        if (order == null){
            return Msg.fail().add("msg","该订单编号不存在");
        }
        //根据订单编号查找需求
        List<UserNeed> needs = userNeedService.selectByOrderNo(orderNo);
        //封装订单Bean
        OrderDetail od = new OrderDetail();
        od.setCreateTime(order.getCreateTime());
        od.setHomeTime(order.getDeliveryTime());
        od.setOrderId(order.getOrderId());
        od.setReceiver(order.getReceiver());
        od.setReceiverAddress(order.getReceiverAddress());
        od.setReceiverPhone(order.getReceivingPhone());
        od.setOrderNo(order.getOrderNo());
        od.setOrderNote(order.getParam1());
        od.setUserNeeds(needs);

        return Msg.success().add("order",od);
    }





    /**
     * 师傅接单接口
     * @return
     */
    @RequestMapping(value = "/getOrderHavePriceForMaster",method = RequestMethod.POST)
    public Msg getOrderHavePriceForMaster(HttpServletRequest request) throws IOException {

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


        String orderNo = jsonObject.getString("orderNo");
        String masterId = jsonObject.getString("masterId");
        String money = jsonObject.getString("money");

        if (orderNo == null || "".equals(orderNo.trim())){
            return Msg.fail().add("msg","参数错误  -1");
        }
        if (masterId == null){
            return Msg.fail().add("msg","参数错误  -2");
        }

        Master master = masterService.selectByPrimaryKey(Integer.parseInt(masterId));
        //判断上下班状态
        if (master.getOfficeType() != 1){
            return Msg.fail().add("msg","请先上班");
        }
        MasterQuote mq = new MasterQuote();
        mq.setMasterId(Integer.parseInt(masterId));
        mq.setOrderNum(orderNo);
        mq.setStatus(1);
        MasterQuote quote = orderService.selectOrderQuoteByPrimary(mq);
        if(quote != null){
            return Msg.fail().add("msg","你已经报过价了");
        }

        mq.setMoney(Double.valueOf(money));
        mq.setTime(OfTime.getLongTime());
        masterService.insertMasterQuote(mq);
        return Msg.success();
    }


    @RequestMapping(value = "/")
    public Msg getss(){


        return Msg.success();
    }






}
