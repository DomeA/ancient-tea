package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.dto.response;

public enum ResultStatusCode {
    OK(200, "OK"),
    SYSTEM_ERR(30001, "System error"),
    INVALID_CLIENTID(30003, "Invalid clientid"),
    INVALID_USERNAME_OR_PASSWORD(30004, "User name or password is incorrect"),
    INVALID_CAPTCHA(30005, "Invalid captcha or captcha overdue"),
    INVALID_TOKEN(30006, "Invalid token"),
    INVALID_USERNAME(30007,"Invalid User name");

    private int code;
    private String msg;
    public int getCode() {
        return code;
    }

    public void setCode(int errcode) {
        this.code = errcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    ResultStatusCode(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }
}
