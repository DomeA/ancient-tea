package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services;

import com.domeastudio.mappingo.servers.microservice.surveying.dto.request.ProcessDefType;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.util.List;
import java.util.zip.ZipInputStream;

public interface WorkFlow {
    Deployment deploymentProcessDefinition(String name, String resourceName, ZipInputStream zipInputStream, byte[] bytes, String text, BpmnModel bpmnModel, String path, ProcessDefType processDefType);

    ProcessInstance startProcessInstanceById(String id);

    ProcessInstance startProcessInstanceByKey(String key);

    List<ProcessDefinition> findAllProcessDefinition();

    Task getTask(String id);

    /**
     * 删除流程定义(删除key相同的所有不同版本的流程定义)
     */
    void deleteProcessDefinitionByKey(String key);

    void deleteProcessDefinitionById(String id);

    ProcessInstance isProcessEnd(String id);

    List<HistoricTaskInstance> findHistoryTasks();

    List<Task> findPersonalTask(String assignee);

    List<Task> findGroupTaskList(String groupName);
}
