package com.domeastudio.mappingo.servers.microservice.surveying.dto.request;

public class ProcessDef {
    private String name;
    private ProcessDefType fileType;
    private String fileId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public ProcessDefType getFileType() {
        return fileType;
    }

    public void setFileType(ProcessDefType fileType) {
        this.fileType = fileType;
    }
}
