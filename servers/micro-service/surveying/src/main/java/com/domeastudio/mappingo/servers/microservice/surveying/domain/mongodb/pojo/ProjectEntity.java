package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Document(collection="sys_workflow_info")
public class ProjectEntity {
    @Id
    private String id;
    private List<FileEntity> fileEntity;
    private String name;
    private Date createBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FileEntity> getFileEntity() {
        return fileEntity;
    }

    public void setFileEntity(List<FileEntity> fileEntity) {
        this.fileEntity = fileEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Date createBy) {
        this.createBy = createBy;
    }
}
