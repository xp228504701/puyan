package com.py.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Demand {
    private Integer id;

    private Integer label;

    private String name;

    private String remark;

    private Integer smallClass;

    private String labelId;

    private String labelName;

    private String labelParam;

    private Date time;

    private Integer type;

    private List<DemandItems> radioItems;

    private List<DemandItems> selectionItems;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSmallClass() {
        return smallClass;
    }

    public void setSmallClass(Integer smallClass) {
        this.smallClass = smallClass;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId == null ? null : labelId.trim();
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    public String getLabelParam() {
        return labelParam;
    }

    public void setLabelParam(String labelParam) {
        this.labelParam = labelParam == null ? null : labelParam.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }


    public void setType(Integer type) {
        this.type = type;
    }

    public List<DemandItems> getRadioItems() {
        return radioItems;
    }

    public void setRadioItems(List<DemandItems> radioItems) {
        this.radioItems = radioItems;
    }

    public List<DemandItems> getSelectionItems() {
        return selectionItems;
    }

    public void setSelectionItems(List<DemandItems> selectionItems) {
        this.selectionItems = selectionItems;
    }
}