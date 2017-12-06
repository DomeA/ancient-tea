package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.impl;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.BpmnFileEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.FileEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.ProjectEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.repository.BpmnFileRepository;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.repository.FileRepository;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.repository.ProjectRepository;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.FileService;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {
    @Autowired
    BpmnFileRepository bpmnFileRepository;
    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public FileEntity saveFile(FileEntity fileEntity) {
        return fileRepository.save(fileEntity);
    }

    @Override
    public ProjectEntity saveProject(ProjectEntity projectEntity) {
        return projectRepository.save(projectEntity);
    }

    @Override
    public void removeProject(String id) {
        projectRepository.delete(id);
    }

    @Override
    public void removeFile(String id) {
        fileRepository.delete(id);
    }

    @Override
    public FileEntity getFileById(String id) {
        return fileRepository.findOne(id);
    }

    @Override
    public ProjectEntity getProjectById(String id) {
        return projectRepository.findOne(id);
    }

    @Override
    public BpmnFileEntity saveBpmnFile(BpmnFileEntity file) {
        return bpmnFileRepository.save(file);
    }

    @Override
    public void removeBpmnFile(String id) {
        bpmnFileRepository.delete(id);
    }

    @Override
    public BpmnFileEntity getBpmnFileById(String id) {
        return bpmnFileRepository.findOne(id);
    }

    @Override
    public List<BpmnFileEntity> listBpmnFilesByPage(int pageIndex, int pageSize) {
        Page<BpmnFileEntity> page = null;
        List<BpmnFileEntity> list = null;

        Sort sort = new Sort(Direction.DESC, "uploadDate");
        Pageable pageable = new PageRequest(pageIndex, pageSize, sort);

        page = bpmnFileRepository.findAll(pageable);
        list = page.getContent();
        return list;
    }

    @Override
    public String gridFSInput(Class cla, File file) {
        DB db = mongoOperations.getCollection(
                mongoOperations.getCollectionName(cla)).getDB();
        //db.requestStart();
        GridFSInputFile gfsInput;
        try {
            gfsInput = new GridFS(db, "fs")
                    .createFile(file);
            gfsInput.setFilename(UUID.randomUUID().toString().replace("-", ""));// 保存到数据库的文件名为qq123456789logo
            gfsInput.save();
            return gfsInput.getFilename();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        //  db.requestDone();
    }

    @Override
    public String gridFSInput(Class cla, InputStream inputStream) {
        DB db = mongoOperations.getCollection(
                mongoOperations.getCollectionName(cla)).getDB();
        //db.requestStart();
        GridFSInputFile gfsInput;
        gfsInput = new GridFS(db, "fs")
                .createFile(inputStream);
        gfsInput.setFilename(UUID.randomUUID().toString().replace("-", ""));// 保存到数据库的文件名为qq123456789logo
        gfsInput.save();
        return gfsInput.getFilename();
    }

    @Override
    public String gridFSInput(Class cla, byte[] content) {
        DB db = mongoOperations.getCollection(
                mongoOperations.getCollectionName(cla)).getDB();
        //db.requestStart();
        GridFSInputFile gfsInput;
        gfsInput = new GridFS(db, "fs")
                .createFile(content);
        gfsInput.setFilename(UUID.randomUUID().toString().replace("-", ""));// 保存到数据库的文件名为qq123456789logo
        gfsInput.save();
        return gfsInput.getFilename();
    }

    @Override
    public void gridFSOutput(ObjectId id, Class cla, OutputStream outputFilepath) {
        DB db = mongoOperations.getCollection(
                mongoOperations.getCollectionName(cla)).getDB();
        GridFSDBFile gfsFile = new GridFS(db, "fs").findOne(id);// 查找文件名qq123456789logo输出保存
        try {
            gfsFile.writeTo(outputFilepath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void gridFSOutput(String name, Class cla, OutputStream outputFilepath) {
        DB db = mongoOperations.getCollection(
                mongoOperations.getCollectionName(cla)).getDB();

        GridFSDBFile gfsFile = new GridFS(db, "fs").findOne(name);// 查找文件名qq123456789logo输出保存
        try {
            gfsFile.writeTo(outputFilepath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
