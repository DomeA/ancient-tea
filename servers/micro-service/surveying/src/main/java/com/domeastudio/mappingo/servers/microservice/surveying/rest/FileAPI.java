package com.domeastudio.mappingo.servers.microservice.surveying.rest;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.FileEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.ProjectEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.FileService;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.request.ProjectDef;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.response.ClientMessage;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.response.ResultStatusCode;
import com.domeastudio.mappingo.servers.microservice.surveying.util.DateUtil;
import com.domeastudio.mappingo.servers.microservice.surveying.util.security.MD5SHAHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/manager/file")
public class FileAPI {
    private static final Logger logger = LoggerFactory.getLogger(FileAPI.class);

    @Autowired
    private FileService fileService;

    //文件上传相关代码
    //@RequestBody要求客户端发过来的是json数据 form表单请求不需要
    @PostMapping(value = "/upload")
    public ClientMessage upload(ProjectDef projectDef, @RequestParam("file") MultipartFile[] multipartFiles) {
        ClientMessage clientMessage;
        if (multipartFiles.length < 1) {
            clientMessage = new ClientMessage(ResultStatusCode.INVALID_FILES.getCode(),
                    ResultStatusCode.INVALID_FILES.getMsg(), null);
            return clientMessage;
        }
        //BufferedOutputStream stream=null;
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setUploadTime(new Date());
        projectEntity.setCreateTime(DateUtil.stringToDate(projectDef.getCreateTime()));
        projectEntity.setName(projectDef.getName());
        projectEntity.setProps(projectDef.getPros());
        List<FileEntity> fileEntities = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            if (!file.isEmpty()) {
                try {
                    FileEntity fileEntity = new FileEntity();
                    fileEntity.setName(file.getOriginalFilename());
                    fileEntity.setUploadDate(new Date());
                    fileEntity.setSize(file.getSize());
                    fileEntity.setContentType(file.getContentType());
                    fileEntity.setMd5(MD5SHAHelper.toString(MD5SHAHelper.encryptByMD5(file.getInputStream())));
                    String fileUUID = fileService.gridFSInput(FileEntity.class, file.getInputStream());
                    fileEntity.setFileNameUUID(fileUUID);
                    FileEntity filetemp = fileService.saveFile(fileEntity);
//                    byte[] bytes = file.getBytes();
//                    stream = new BufferedOutputStream(new FileOutputStream(
//                            new File(file.getOriginalFilename())));
//                    stream.write(bytes);
//                    stream.close();
                    fileEntities.add(filetemp);

                } catch (Exception e) {
                    //stream = null;
                    clientMessage = new ClientMessage(ResultStatusCode.SYSTEM_ERR.getCode(),
                            ResultStatusCode.SYSTEM_ERR.getMsg(), null);
                    return clientMessage;
                }
            } else {
                clientMessage = new ClientMessage(ResultStatusCode.INVALID_FILE.getCode(),
                        ResultStatusCode.INVALID_FILE.getMsg(), null);
                return clientMessage;
            }
        }
        projectEntity.setFileEntity(fileEntities);
        ProjectEntity p = fileService.saveProject(projectEntity);

        clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                ResultStatusCode.OK.getMsg(), p);
        return clientMessage;
    }

    //文件下载相关代码
    @RequestMapping(value = "/download/{fid}", method = RequestMethod.GET)
    public ClientMessage downloadFile(@PathVariable("fid") String fid, HttpServletResponse httpServletResponse) throws IOException {
        FileEntity fileEntity = fileService.getFileById(fid);
        ClientMessage clientMessage;
        if (fileEntity == null) {
            clientMessage = new ClientMessage(ResultStatusCode.INVALID_FILE_ID.getCode(),
                    ResultStatusCode.INVALID_FILE_ID.getMsg(), null);
            return clientMessage;
        }
        httpServletResponse.setContentType("application/force-download");// 设置强制下载不打开
        httpServletResponse.addHeader("Content-Disposition",
                "attachment;fileName=" + fileEntity.getName() + "_" + fileEntity.getFileNameUUID());// 设置文件名
        OutputStream os = null;
        try {
            os = httpServletResponse.getOutputStream();
            fileService.gridFSOutput(fileEntity.getFileNameUUID(), FileEntity.class, os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            os.close();
        }
        return null;
    }
}
