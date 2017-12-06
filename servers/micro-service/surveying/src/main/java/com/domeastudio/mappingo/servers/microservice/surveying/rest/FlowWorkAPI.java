package com.domeastudio.mappingo.servers.microservice.surveying.rest;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.BpmnFileEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.FileService;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.WorkFlow;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.request.ProcessDef;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.response.ClientMessage;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.response.ResultStatusCode;
import com.domeastudio.mappingo.servers.microservice.surveying.util.ByteAndInputStreamUtil;
import io.swagger.annotations.Api;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.zip.ZipInputStream;

@Api("工作流服务")
@RestController
@RequestMapping("/manager/flowable")
public class FlowWorkAPI {

    @Autowired
    private WorkFlow workFlow;
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/process/deployment",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClientMessage deploymentProcessDefinition(@RequestBody ProcessDef processDef) {
        ClientMessage clientMessage;
        Deployment deployment;
        BpmnFileEntity bpmnFileEntity =fileService.getBpmnFileById(processDef.getFileId());
        ZipInputStream zipIn = new ZipInputStream( ByteAndInputStreamUtil.byte2Input(bpmnFileEntity.getContent()));
        //todo:修改定义参数
        deployment = workFlow.deploymentProcessDefinition(
                processDef.getName(), null,
                zipIn, null,
                null, null, null, processDef.getFileType());
        if (deployment == null) {
            clientMessage = new ClientMessage(ResultStatusCode.FAILURE_PROCESS_DEFINITION.getCode(),
                    ResultStatusCode.FAILURE_PROCESS_DEFINITION.getMsg(), null);
            return clientMessage;
        }
        System.out.println("##########流程部署##########");
        System.out.println("部署ID:" + deployment.getId());
        System.out.println("部署名称：" + deployment.getName());
        System.out.println("################################");
        clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                ResultStatusCode.OK.getMsg(), deployment);
        return clientMessage;
    }

    @RequestMapping(value = "/process/start/{param}", method = RequestMethod.GET)
    public ClientMessage startProcessInstanceById(@PathVariable String param) {
        ClientMessage clientMessage;
        ProcessInstance processInstance;
        if (workFlow.startProcessInstanceById(param) != null) {
            processInstance = workFlow.startProcessInstanceById(param);
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                    ResultStatusCode.OK.getMsg(), processInstance);
            return clientMessage;
        } else if (workFlow.startProcessInstanceByKey(param) != null) {
            processInstance = workFlow.startProcessInstanceByKey(param);
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                    ResultStatusCode.OK.getMsg(), processInstance);
            return clientMessage;
        } else {
            clientMessage = new ClientMessage(ResultStatusCode.INVALID_PROCESS_ID_OR_KEY.getCode(),
                    ResultStatusCode.INVALID_PROCESS_ID_OR_KEY.getMsg(), null);
            return clientMessage;
        }
    }

    @RequestMapping(value = "/process/findall", method = RequestMethod.GET)
    public ClientMessage getAllProcessInstance() {
        ClientMessage clientMessage;
        if (workFlow.findAllProcessDefinition() != null) {
            List<ProcessDefinition> processInstances = workFlow.findAllProcessDefinition();
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                    ResultStatusCode.OK.getMsg(), processInstances);
        } else {
            clientMessage = new ClientMessage(ResultStatusCode.NONE_PROCESSES.getCode(),
                    ResultStatusCode.NONE_PROCESSES.getMsg(), null);
        }
        return clientMessage;
    }

    @RequestMapping(value = "/process/delete/{param}", method = RequestMethod.DELETE)
    public ClientMessage deleteProcessDefinition(@PathVariable String param) {
        ClientMessage clientMessage;
        try {
            workFlow.deleteProcessDefinitionById(param);
            workFlow.deleteProcessDefinitionByKey(param);
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), null);
            return clientMessage;
        } catch (Exception e) {
            e.printStackTrace();
            clientMessage = new ClientMessage(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getMsg(), null);
            return clientMessage;
        }
    }

    @RequestMapping(value = "/process/state/{id}", method = RequestMethod.GET)
    public ClientMessage isProcessEnd(String id) {
        ClientMessage clientMessage;
        ProcessInstance processInstance = workFlow.isProcessEnd(id);
        if (processInstance != null) {
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                    ResultStatusCode.OK.getMsg(), processInstance);
        } else {
            clientMessage = new ClientMessage(ResultStatusCode.NONE_PROCESSES.getCode(),
                    ResultStatusCode.NONE_PROCESSES.getMsg(), null);
        }
        return clientMessage;
    }

    @RequestMapping(value = "/task/{param}", method = RequestMethod.GET)
    public ClientMessage getTask(@PathVariable String param) {
        ClientMessage clientMessage;
        if (workFlow.getTask(param) != null) {
            Task task = workFlow.getTask(param);
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                    ResultStatusCode.OK.getMsg(), task);
        } else {
            clientMessage = new ClientMessage(ResultStatusCode.NONE_TASK.getCode(),
                    ResultStatusCode.NONE_TASK.getMsg(), null);
        }
        return clientMessage;
    }

    @RequestMapping(value = "/task/mytask/{name}", method = RequestMethod.GET)
    public ClientMessage findMyPersonTask(@PathVariable String param) {
        ClientMessage clientMessage;
        List<Task> tasks = workFlow.findPersonalTask(param);
        if (tasks != null) {
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                    ResultStatusCode.OK.getMsg(), tasks);
        } else {
            clientMessage = new ClientMessage(ResultStatusCode.NONE_TASK.getCode(),
                    ResultStatusCode.NONE_TASK.getMsg(), null);
        }
        return clientMessage;
    }

    @RequestMapping(value = "/task/histories", method = RequestMethod.GET)
    public ClientMessage findHistoryTasks() {
        ClientMessage clientMessage;
        List<HistoricTaskInstance> tasks = workFlow.findHistoryTasks();
        if (tasks != null) {
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                    ResultStatusCode.OK.getMsg(), tasks);
        } else {
            clientMessage = new ClientMessage(ResultStatusCode.NONE_HISTORY_TASK.getCode(),
                    ResultStatusCode.NONE_HISTORY_TASK.getMsg(), null);
        }
        return clientMessage;
    }

    @RequestMapping(value = "/task/group/{name}", method = RequestMethod.GET)
    public ClientMessage findGroupTaskList(@PathVariable String name) {
        ClientMessage clientMessage;
        List<Task> tasks = workFlow.findGroupTaskList(name);
        if (tasks != null) {
            clientMessage = new ClientMessage(ResultStatusCode.OK.getCode(),
                    ResultStatusCode.OK.getMsg(), tasks);
        } else {
            clientMessage = new ClientMessage(ResultStatusCode.NONE_GROUP_TASK.getCode(),
                    ResultStatusCode.NONE_GROUP_TASK.getMsg(), null);
        }
        return clientMessage;
    }

//    @RequestMapping("/")
//    String index() {
//        System.out.println("################################taskService" + taskService);
//        System.out.println("################################processEngine" + processEngine);
//        return "xxxxxxxxxxxxx";
//    }
}
