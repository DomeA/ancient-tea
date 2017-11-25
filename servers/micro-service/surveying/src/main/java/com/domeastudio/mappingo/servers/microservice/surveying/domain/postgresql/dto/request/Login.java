package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.dto.request;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("login")
public class Login {
    private String name;
    private String pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
