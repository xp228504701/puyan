package com.py.bean;

public class Setting {
    private Integer id;

    private String payDeposit;

    private String amountRatio;

    private String transferRatio;

    private String parameter1;

    private String parameter2;

    private String parameter3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayDeposit() {
        return payDeposit;
    }

    public void setPayDeposit(String payDeposit) {
        this.payDeposit = payDeposit == null ? null : payDeposit.trim();
    }

    public String getAmountRatio() {
        return amountRatio;
    }

    public void setAmountRatio(String amountRatio) {
        this.amountRatio = amountRatio == null ? null : amountRatio.trim();
    }

    public String getTransferRatio() {
        return transferRatio;
    }

    public void setTransferRatio(String transferRatio) {
        this.transferRatio = transferRatio == null ? null : transferRatio.trim();
    }

    public String getParameter1() {
        return parameter1;
    }

    public void setParameter1(String parameter1) {
        this.parameter1 = parameter1 == null ? null : parameter1.trim();
    }

    public String getParameter2() {
        return parameter2;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2 == null ? null : parameter2.trim();
    }

    public String getParameter3() {
        return parameter3;
    }

    public void setParameter3(String parameter3) {
        this.parameter3 = parameter3 == null ? null : parameter3.trim();
    }
}