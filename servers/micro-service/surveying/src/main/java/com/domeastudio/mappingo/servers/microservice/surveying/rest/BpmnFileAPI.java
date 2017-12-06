package com.domeastudio.mappingo.servers.microservice.surveying.rest;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.BpmnFileEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.FileService;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.response.ClientMessage;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.response.ResultStatusCode;
import com.domeastudio.mappingo.servers.microservice.surveying.util.DateUtil;
import com.domeastudio.mappingo.servers.microservice.surveying.util.security.MD5SHAHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/manager/file")
public class BpmnFileAPI {

    @Autowired
    private FileService fileService;

    /**
     * 分页查询文件
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/bpmn/{pageIndex}/{pageSize}")
    @ResponseBody
    public ClientMessage listFilesByPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        ClientMessage clientMessage;
        List<BpmnFileEntity> bpmnFileEntities = fileService.listBpmnFilesByPage(pageIndex, pageSize);
        if (bpmnFileEntities == null) {
            clientMessage = new ClientMessage(ResultStatusCode.NONE_FILE.getCode(),
                    ResultStatusCode.NONE_FILE.getMsg(), null);
            return clientMessage;
        }
        clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                ResultStatusCode.OK.getMsg(), bpmnFileEntities);
        return clientMessage;
    }

    /**
     * 获取文件片信息
     *
     * @param id
     * @return
     */
    @GetMapping("/bpmn/files/{id}")
    @ResponseBody
    public ResponseEntity<Object> getFile(@PathVariable String id) {

        BpmnFileEntity file = fileService.getBpmnFileById(id);

        if (file != null) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + file.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_LENGTH, file.getSize() + "")
                    .header("Connection", "close")
                    .body(file.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }

    }

    /**
     * 在线显示文件
     *
     * @param id
     * @return
     */
    @GetMapping("/bpmn/view/{id}")
    @ResponseBody
    public ResponseEntity<Object> getFileOnline(@PathVariable String id) {

        BpmnFileEntity file = fileService.getBpmnFileById(id);

        if (file != null) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, file.getSize() + "")
                    .header("Connection", "close")
                    .body(file.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }

    }

    /**
     * 上传
     *
     * @param file
     * @return
     */
    @PostMapping("/bpmn/upload")
    public ClientMessage handleFileUpload(@RequestParam("file") MultipartFile file) {
        ClientMessage clientMessage;
        try {
            BpmnFileEntity bpmnFileEntity = new BpmnFileEntity();
            bpmnFileEntity.setName(file.getOriginalFilename());
            bpmnFileEntity.setContentType(file.getContentType());
            bpmnFileEntity.setSize(file.getSize());
            bpmnFileEntity.setContent(file.getBytes());
            bpmnFileEntity.setUploadDate(new Date());
            bpmnFileEntity.setMd5(MD5SHAHelper.toString(MD5SHAHelper.encryptByMD5(file.getInputStream())));
            BpmnFileEntity bpmnFile = fileService.saveBpmnFile(bpmnFileEntity);
            if (bpmnFile == null) {
                clientMessage = new ClientMessage(ResultStatusCode.INVALID_FILE.getCode(),
                        ResultStatusCode.INVALID_FILE.getMsg(), null);
                return clientMessage;
            }
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                    ResultStatusCode.OK.getMsg(), bpmnFile.getId());
            return clientMessage;
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            clientMessage = new ClientMessage(ResultStatusCode.INVALID_FILE.getCode(),
                    ResultStatusCode.INVALID_FILE.getMsg(), null);
            return clientMessage;
        }
    }

//    /**
//     * 上传接口
//     * @param file
//     * @return
//     */
//    @PostMapping("/upload")
//    @ResponseBody
//    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
//        BpmnFileEntity returnFile = null;
//        try {
//            BpmnFileEntity bpmnFileEntity = new BpmnFileEntity();
//            bpmnFileEntity.setName(file.getOriginalFilename());
//            bpmnFileEntity.setContentType(file.getContentType());
//            bpmnFileEntity.setSize(file.getSize());
//            bpmnFileEntity.setContent(file.getBytes());
//            bpmnFileEntity.setUploadDate(new Date());
//            bpmnFileEntity.setMd5(MD5SHAHelper.toString(MD5SHAHelper.encryptByMD5(file.getInputStream())));
//            fileService.saveBpmnFile(bpmnFileEntity);
//            String path = "//"+ serverAddress + ":" + serverPort + "/view/"+returnFile.getId();
//            return ResponseEntity.status(HttpStatus.OK).body(path);
//
//        } catch (IOException | NoSuchAlgorithmException ex) {
//            ex.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//        }
//
//    }

    /**
     * 删除文件
     *
     * @param id
     * @return
     */
    @DeleteMapping("/bpmn/delete/{id}")
    @ResponseBody
    public ClientMessage deleteFile(@PathVariable String id) {
        ClientMessage clientMessage;
        try {
            fileService.removeBpmnFile(id);
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                    ResultStatusCode.OK.getMsg(), null);
            return clientMessage;
        } catch (Exception e) {
            e.printStackTrace();
            clientMessage = new ClientMessage(ResultStatusCode.SYSTEM_ERR.getCode(),
                    ResultStatusCode.SYSTEM_ERR.getMsg(), null);
            return clientMessage;
        }
    }
}
