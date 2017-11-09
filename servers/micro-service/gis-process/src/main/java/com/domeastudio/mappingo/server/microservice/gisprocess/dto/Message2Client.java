package com.domeastudio.mappingo.server.microservice.gisprocess.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@JsonRootName("message2client")
public class Message2Client implements Serializable {
    private Object message;
    private String code;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
