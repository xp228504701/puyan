package com.py.bean;

import java.util.Date;

public class MasterCertification {
    private Integer id;

    private Integer masterId;

    private String certificationId;

    private Date time;

    private String param1;

    private Integer majorClassId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public String getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(String certificationId) {
        this.certificationId = certificationId == null ? null : certificationId.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1 == null ? null : param1.trim();
    }

    public Integer getMajorClassId() {
        return majorClassId;
    }

    public void setMajorClassId(Integer majorClassId) {
        this.majorClassId = majorClassId;
    }
}