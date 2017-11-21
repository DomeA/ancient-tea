package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools.core;

import ucar.ma2.DataType;
import ucar.nc2.Attribute;
import ucar.nc2.Group;

import java.util.List;

public class AxisObject {
    private String dimName;
    private Integer range;
    private List<Attribute> attributes;
    private Group group;
    private DataType dataType;
    private List<Object> objectDatas;

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getDimName() {
        return dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public List<Object> getObjectDatas() {
        return objectDatas;
    }

    public void setObjectDatas(List<Object> objectDatas) {
        this.objectDatas = objectDatas;
    }
}
