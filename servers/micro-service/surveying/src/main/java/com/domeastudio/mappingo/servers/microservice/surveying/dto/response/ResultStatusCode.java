package com.domeastudio.mappingo.servers.microservice.surveying.dto.response;

public enum ResultStatusCode {
    OK(200, "OK"),
    SYSTEM_ERR(30001, "System error"),
    INVALID_CLIENTID(30003, "Invalid clientid"),
    INVALID_USERNAME_OR_PASSWORD(30004, "User name or password is incorrect"),
    INVALID_CAPTCHA(30005, "Invalid captcha or captcha overdue"),
    INVALID_TOKEN(30006, "Invalid token"),
    INVALID_TIME(30008, "Invalid time limit"),
    INVALID_USERNAME(30007, "Invalid User name"),
    FAILURE_PROCESS_DEFINITION(40001, "Process definition failure"),
    INVALID_PROCESS_ID_OR_KEY(40002, "Invalid process id or key"),
    NONE_PROCESSES(40003, "There is no process"),
    NONE_TASK(40004, "There is no task"),
    NONE_HISTORY_TASK(40005, "There is no history task"),
    NONE_GROUP_TASK(40006, "There is no group task"),
    NONE_FILE(50001, "There is no files"),
    INVALID_FILES(50002, "Invalid file collection"),
    INVALID_FILE(50003, "Invalid file"),
    INVALID_FILE_ID(50004, "Invalid file id"),
    INVALID_PROJECT(50005, "Invalid project");

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

    ResultStatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
