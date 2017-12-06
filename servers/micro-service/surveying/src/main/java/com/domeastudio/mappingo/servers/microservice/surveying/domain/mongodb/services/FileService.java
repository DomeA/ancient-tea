package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.BpmnFileEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.FileEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.ProjectEntity;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface FileService {

    FileEntity saveFile(FileEntity fileEntity);

    ProjectEntity saveProject(ProjectEntity projectEntity);

    void removeProject(String id);

    void removeFile(String id);

    FileEntity getFileById(String id);

    ProjectEntity getProjectById(String id);


    /**
     * 保存文件
     *
     * @param file
     * @return
     */
    BpmnFileEntity saveBpmnFile(BpmnFileEntity file);

    /**
     * 删除文件
     *
     * @param id
     */
    void removeBpmnFile(String id);

    /**
     * 根据id获取文件
     *
     * @param id
     * @return
     */
    BpmnFileEntity getBpmnFileById(String id);

    /**
     * 分页查询，按上传时间降序
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<BpmnFileEntity> listBpmnFilesByPage(int pageIndex, int pageSize);

    String gridFSInput(Class cla, File file);

    String gridFSInput(Class cla, InputStream inputStream);

    String gridFSInput(Class cla, byte[] content);

    void gridFSOutput(ObjectId id, Class cla, OutputStream outputFilepath);

    void gridFSOutput(String name, Class cla, OutputStream outputFilepath);
}
