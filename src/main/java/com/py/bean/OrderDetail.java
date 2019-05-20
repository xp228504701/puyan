package com.py.bean;

import java.util.Date;
import java.util.List;

/**
 * 订单信息(详情)
 */
public class OrderDetail {

    private Integer orderId; //订单id

    private String orderNo; //订单编号

    private String createTime; //订单创建时间

    private String homeTime; //上门时间

    private String receiverAddress; //服务地址

    private String receiver; //联系人

    private String receiverPhone; //联系电话

    private String orderNote; //需求备注信息

    private List<UserNeed> userNeeds; //需求内容


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHomeTime() {
        return homeTime;
    }

    public void setHomeTime(String homeTime) {
        this.homeTime = homeTime;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public List<UserNeed> getUserNeeds() {
        return userNeeds;
    }

    public void setUserNeeds(List<UserNeed> userNeeds) {
        this.userNeeds = userNeeds;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }
}
