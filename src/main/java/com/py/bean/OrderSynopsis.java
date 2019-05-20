package com.py.bean;

public class OrderSynopsis {

    private String orderType;       // 订单类型

    private String orderDepict;     // 订单描述

    private String orderAddress;    // 订单地址

    private String orderTime;       // 订单时间

    private Integer orderId;        // 订单id

    private String orderNo;         // 订单编号

    private Integer state;







    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderDepict() {
        return orderDepict;
    }

    public void setOrderDepict(String orderDepict) {
        this.orderDepict = orderDepict;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
