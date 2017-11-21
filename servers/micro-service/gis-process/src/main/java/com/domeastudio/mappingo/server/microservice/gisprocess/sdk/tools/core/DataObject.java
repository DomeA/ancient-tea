package com.domeastudio.mappingo.server.microservice.gisprocess.sdk.tools.core;

import ucar.ma2.DataType;
import ucar.nc2.Attribute;

import java.util.List;

public class DataObject {
    private String shortName;
    private DataType dataType;
    private List<Attribute> attributes;
    private List<Object> objectDatas;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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


    public List<Object> getObjectDatas() {
        return objectDatas;
    }

    public void setObjectDatas(List<Object> objectDatas) {
        this.objectDatas = objectDatas;
    }
}
