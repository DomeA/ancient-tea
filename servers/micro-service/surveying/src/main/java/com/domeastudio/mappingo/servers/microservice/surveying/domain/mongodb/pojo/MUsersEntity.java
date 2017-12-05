package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo;

import com.mongodb.gridfs.GridFS;
import com.mongodb.util.JSON;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Document(collection="sys_workflow_info")
public class MUsersEntity {
    @Id
    private String id;

    private GridFS file;

    private String name;

    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return JSON.serialize(this);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
