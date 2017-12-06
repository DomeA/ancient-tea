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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        if (multipartFiles.length<1){
            clientMessage=new ClientMessage(ResultStatusCode.INVALID_FILES.getCode(),
                    ResultStatusCode.INVALID_FILES.getMsg(),null);
            return clientMessage;
        }
        //BufferedOutputStream stream=null;
        ProjectEntity projectEntity=new ProjectEntity();
        projectEntity.setUploadTime(new Date());
        projectEntity.setCreateTime(DateUtil.stringToDate(projectDef.getCreateTime()));
        projectEntity.setName(projectDef.getName());
        projectEntity.setProps(projectDef.getPros());
        List<FileEntity> fileEntities=new ArrayList<>();
        for (MultipartFile file:multipartFiles) {
            if (!file.isEmpty()) {
                try {
                    FileEntity fileEntity=new FileEntity();
                    fileEntity.setName(file.getOriginalFilename());
                    fileEntity.setUploadDate(new Date());
                    fileEntity.setSize(file.getSize());
                    fileEntity.setContentType(file.getContentType());
                    fileEntity.setMd5(MD5SHAHelper.toString(MD5SHAHelper.encryptByMD5(file.getInputStream())));
                    FileEntity filetemp = fileService.saveFile(fileEntity);
                    fileService.gridFSInput(filetemp.getId(),FileEntity.class,file.getInputStream());
//                    byte[] bytes = file.getBytes();
//                    stream = new BufferedOutputStream(new FileOutputStream(
//                            new File(file.getOriginalFilename())));
//                    stream.write(bytes);
//                    stream.close();
                    fileEntities.add(filetemp);

                } catch (Exception e) {
                    //stream = null;
                    clientMessage=new ClientMessage(ResultStatusCode.SYSTEM_ERR.getCode(),
                            ResultStatusCode.SYSTEM_ERR.getMsg(),null);
                    return clientMessage;
                }
            } else {
                clientMessage=new ClientMessage(ResultStatusCode.INVALID_FILE.getCode(),
                        ResultStatusCode.INVALID_FILE.getMsg(),null);
                return clientMessage;
            }
        }
        projectEntity.setFileEntity(fileEntities);
        ProjectEntity p = fileService.saveProject(projectEntity);

        clientMessage=new ClientMessage(ResultStatusCode.OK.getCode(),
                ResultStatusCode.OK.getMsg(),p.getId());
        return clientMessage;
    }

    //文件下载相关代码
    @RequestMapping(value = "/download/{id}",method = RequestMethod.GET)
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "FileUploadTests.java";
        if (fileName != null) {
            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
            String realPath = request.getServletContext().getRealPath(
                    "//WEB-INF//");
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
    //多文件上传
    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();

                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => "
                            + e.getMessage();
                }
            } else {
                return "You failed to upload " + i
                        + " because the file was empty.";
            }
        }
        return "upload successful";
    }
}
