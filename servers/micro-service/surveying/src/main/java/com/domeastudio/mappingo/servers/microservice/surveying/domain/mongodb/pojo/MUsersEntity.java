package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo;

import com.mongodb.util.JSON;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Document(collection="order_info")
public class MUsersEntity {
    @Id
    private String id;

    private String name;

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
}
