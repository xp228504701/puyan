package com.py.bean;

public class H5indexInfo {
    private Integer id;

    private String logoImg;

    private String bannerImg;

    private String processImg;

    private String companyName;

    private String companyAddress;

    private String companyPhone;

    private String companyPostcode;

    private String companyFax;

    private String param1;

    private String param2;

    private String centerImg;

    private String centerTitle;

    private String centerValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg == null ? null : logoImg.trim();
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg == null ? null : bannerImg.trim();
    }

    public String getProcessImg() {
        return processImg;
    }

    public void setProcessImg(String processImg) {
        this.processImg = processImg == null ? null : processImg.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone == null ? null : companyPhone.trim();
    }

    public String getCompanyPostcode() {
        return companyPostcode;
    }

    public void setCompanyPostcode(String companyPostcode) {
        this.companyPostcode = companyPostcode == null ? null : companyPostcode.trim();
    }

    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax == null ? null : companyFax.trim();
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1 == null ? null : param1.trim();
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2 == null ? null : param2.trim();
    }

    public String getCenterImg() {
        return centerImg;
    }

    public void setCenterImg(String centerImg) {
        this.centerImg = centerImg == null ? null : centerImg.trim();
    }

    public String getCenterTitle() {
        return centerTitle;
    }

    public void setCenterTitle(String centerTitle) {
        this.centerTitle = centerTitle == null ? null : centerTitle.trim();
    }

    public String getCenterValue() {
        return centerValue;
    }

    public void setCenterValue(String centerValue) {
        this.centerValue = centerValue == null ? null : centerValue.trim();
    }
}