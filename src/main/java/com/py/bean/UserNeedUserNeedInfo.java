package com.py.bean;

public class UserNeedUserNeedInfo {
    private Integer id;

    private Integer userNeedId;

    private Integer userNeedInfoId;

    private String param;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserNeedId() {
        return userNeedId;
    }

    public void setUserNeedId(Integer userNeedId) {
        this.userNeedId = userNeedId;
    }

    public Integer getUserNeedInfoId() {
        return userNeedInfoId;
    }

    public void setUserNeedInfoId(Integer userNeedInfoId) {
        this.userNeedInfoId = userNeedInfoId;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }
}