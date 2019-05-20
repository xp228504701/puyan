package com.py.bean;

import java.util.Date;

public class Admin {
    private Integer adminId;

    private String adminAccount;

    private String adminPassWord;

    private Date adminCreationTime;

    private String adminFullName;

    private Integer adminStatus;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount == null ? null : adminAccount.trim();
    }

    public String getAdminPassWord() {
        return adminPassWord;
    }

    public void setAdminPassWord(String adminPassWord) {
        this.adminPassWord = adminPassWord == null ? null : adminPassWord.trim();
    }

    public Date getAdminCreationTime() {
        return adminCreationTime;
    }

    public void setAdminCreationTime(Date adminCreationTime) {
        this.adminCreationTime = adminCreationTime;
    }

    public String getAdminFullName() {
        return adminFullName;
    }

    public void setAdminFullName(String adminFullName) {
        this.adminFullName = adminFullName == null ? null : adminFullName.trim();
    }

    public Integer getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Integer adminStatus) {
        this.adminStatus = adminStatus;
    }
}