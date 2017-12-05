package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.BpmnFileEntity;

import java.util.List;

public interface FileService {
    /**
     * 保存文件
     * @param file
     * @return
     */
    BpmnFileEntity saveBpmnFile(BpmnFileEntity file);

    /**
     * 删除文件
     * @param id
     */
    void removeBpmnFile(String id);

    /**
     * 根据id获取文件
     * @param id
     * @return
     */
    BpmnFileEntity getFileById(String id);

    /**
     * 分页查询，按上传时间降序
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<BpmnFileEntity> listFilesByPage(int pageIndex, int pageSize);
}
