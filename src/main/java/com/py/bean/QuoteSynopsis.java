package com.py.bean;

public class QuoteSynopsis {

    private Integer masterQuoteId;    //师傅报价id

    private String orderNo;     //订单编号

    private String storeName;   //接单人店铺名称

    private String storeAddress;   //接单人店铺地址

    private Double money;       //报价金额

    private String time;        //报价时间

    private Integer status;     //报价状态( 1: 报价中  2:接受  3: 驳回报价 4 取消报价)


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMasterQuoteId() {
        return masterQuoteId;
    }

    public void setMasterQuoteId(Integer masterQuoteId) {
        this.masterQuoteId = masterQuoteId;
    }
}
