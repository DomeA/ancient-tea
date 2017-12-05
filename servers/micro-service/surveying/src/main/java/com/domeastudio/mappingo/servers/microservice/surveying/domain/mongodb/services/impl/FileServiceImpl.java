package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.impl;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.BpmnFileEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.repository.BpmnFileRepository;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FileServiceImpl implements FileService {
    @Autowired
    BpmnFileRepository bpmnFileRepository;

    @Override
    public BpmnFileEntity saveBpmnFile(BpmnFileEntity file) {
        return bpmnFileRepository.save(file);
    }

    @Override
    public void removeBpmnFile(String id) {
        bpmnFileRepository.delete(id);
    }

    @Override
    public BpmnFileEntity getFileById(String id) {
        return bpmnFileRepository.findOne(id);
    }

    @Override
    public List<BpmnFileEntity> listFilesByPage(int pageIndex, int pageSize) {
        Page<BpmnFileEntity> page = null;
        List<BpmnFileEntity> list = null;

        Sort sort = new Sort(Direction.DESC,"uploadDate");
        Pageable pageable = new PageRequest(pageIndex, pageSize, sort);

        page = bpmnFileRepository.findAll(pageable);
        list = page.getContent();
        return list;
    }
}
