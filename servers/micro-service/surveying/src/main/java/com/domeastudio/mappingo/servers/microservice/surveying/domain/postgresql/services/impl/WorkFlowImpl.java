package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.impl;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.WorkFlow;
import com.domeastudio.mappingo.servers.microservice.surveying.dto.request.ProcessDefType;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.zip.ZipInputStream;

@Service
public class WorkFlowImpl implements WorkFlow {

    @Autowired
    private TaskService taskService;
    //    @Autowired
//    private ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;

    @Override
    public Deployment deploymentProcessDefinition(String name, String resourceName, ZipInputStream zipInputStream, byte[] bytes, String text, BpmnModel bpmnModel, String path, ProcessDefType processDefType) {
        Deployment deployment = null;
        //创建核心引擎对象
        switch (processDefType) {
            case ZIP:
                deployment = repositoryService
                        .createDeployment()
                        .name(name)
                        .addZipInputStream(zipInputStream)
                        .deploy();
                break;
            case BYTE:
                deployment = repositoryService
                        .createDeployment()
                        .name(name)
                        .addBytes(resourceName, bytes)
                        .deploy();
                break;
            case PATH:
                deployment = repositoryService
                        .createDeployment()
                        .name(name)
                        .addClasspathResource(path)
                        .deploy();
                break;
            case STRING:
                deployment = repositoryService
                        .createDeployment()
                        .name(name)
                        .addString(resourceName, text)
                        .deploy();
                break;
            case BPMNMODEL:
                deployment = repositoryService
                        .createDeployment()
                        .name(name)
                        .addBpmnModel(resourceName, bpmnModel)
                        .deploy();
                break;
        }
        return deployment;
    }

    @Override
    public ProcessInstance startProcessInstanceById(String id) {
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceById(id);
        System.out.println("##########流程开启##########");
        System.out.println("流程实例ID:" + processInstance.getId());
        System.out.println("流程定义ID:" + processInstance.getProcessDefinitionId());

        return processInstance;
    }

    @Override
    public ProcessInstance startProcessInstanceByKey(String key) {
        ProcessInstance processInstance = runtimeService// 于正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(key);// 使用流程定义的key启动流程实例，key对应hellworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID:" + processInstance.getId());// 流程实例ID 101
        System.out.println("流程定义ID:" + processInstance.getProcessDefinitionId()); // 流程定义ID HelloWorld:1:4
        return processInstance;
    }

    @Override
    public List<ProcessDefinition> findAllProcessDefinition() {
        List<ProcessDefinition> list = repositoryService// 与流程定义和部署对象先相关的service
                .createProcessDefinitionQuery()// 创建一个流程定义的查询
                /** 指定查询条件，where条件 */
                // .deploymentId(deploymentId) //使用部署对象ID查询
                // .processDefinitionId(processDefinitionId)//使用流程定义ID查询
                // .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /* 排序 */
                .orderByProcessDefinitionVersion().asc()
                // .orderByProcessDefinitionVersion().desc()

                /* 返回的结果集 */
                .list();// 返回一个集合列表，封装流程定义
        // .singleResult();//返回惟一结果集
        // .count();//返回结果集数量
        // .listPage(firstResult, maxResults);//分页查询

        if (list != null && list.size() > 0) {
            for (ProcessDefinition pd : list) {
                System.out.println("####################所有的流程定义##########################");
                System.out.println("流程定义ID:" + pd.getId());// 流程定义的key+版本+随机生成数
                System.out.println("流程定义的名称:" + pd.getName());// 对应helloworld.bpmn文件中的name属性值
                System.out.println("流程定义的key:" + pd.getKey());// 对应helloworld.bpmn文件中的id属性值
                System.out.println("流程定义的版本:" + pd.getVersion());// 当流程定义的key值相同的相同下，版本升级，默认1
                System.out.println("资源名称bpmn文件:" + pd.getResourceName());
                System.out.println("资源名称png文件:" + pd.getDiagramResourceName());
                System.out.println("部署对象ID：" + pd.getDeploymentId());
                System.out.println("是否暂停：" + pd.isSuspended());
                System.out.println("#########################################################");
            }
        } else {
            System.out.println("没有流程正在运行。");
        }
        return list;
    }

    @Override
    public Task getTask(String id) {
        // 任务ID
        Task task = taskService.newTask(id);
        System.out.println(task.getId() + "    " +
                task.getAssignee() + "    " +
                task.getName() + "    " +
                task.getProcessInstanceId());
        System.out.println("################################");
        return task;
    }

    @Override
    public void deleteProcessDefinitionByKey(String key) {
        // 先使用流程定义的key查询流程定义，查询出所有的版本
        List<ProcessDefinition> list = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(key)// 使用流程定义的key查询
                .list();
        // 遍历，获取每个流程定义的部署ID
        deleteProcessDefinitionList(list);
    }

    @Override
    public void deleteProcessDefinitionById(String id) {
        // 先使用流程定义的key查询流程定义，查询出所有的版本
        List<ProcessDefinition> list = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(id)// 使用流程定义的key查询
                .list();
        // 遍历，获取每个流程定义的部署ID
        deleteProcessDefinitionList(list);
    }

    @Override
    public ProcessInstance isProcessEnd(String id) {
        ProcessInstance processInstance = runtimeService//表示正在执行的流程实例和执行对象
                .createProcessInstanceQuery()//创建流程实例查询
                .processInstanceId(id)//使用流程实例ID查询
                .singleResult();

        if (processInstance == null) {
            System.out.println("流程已经结束");
        } else {
            System.out.println("流程没有结束");
        }
        return processInstance;
    }

    @Override
    public List<HistoricTaskInstance> findHistoryTasks() {
        //        String processInstanceId = "42501";
        List<HistoricTaskInstance> list = historyService//与历史数据（历史表）相关的service
                .createHistoricTaskInstanceQuery()//创建历史任务实例查询
//                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceId().asc()
                .list();

        if (list != null && list.size() > 0) {
            for (HistoricTaskInstance hti : list) {
                System.out.println(hti.getId() + "    " +
                        hti.getAssignee() + "    " +
                        hti.getName() + "    " +
                        hti.getProcessInstanceId() + "   " +
                        hti.getStartTime() + "   " +
                        hti.getEndTime() + "   " +
                        hti.getDurationInMillis());
                System.out.println("################################");
            }
        }
        return list;
    }

    @Override
    public List<Task> findPersonalTask(String assignee) {
        List<Task> list = taskService// 与正在执行的认为管理相关的Service
                .createTaskQuery()// 创建任务查询对象
                .taskAssignee(assignee)// 指定个人认为查询，指定办理人
                .list();

        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间" + task);
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("流程实例ID:" + task.getProcessInstanceId());
                System.out.println("执行对象ID:" + task.getExecutionId());
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
                System.out.println("#################################");
            }
        }
        return list;
    }

    @Override
    public List<Task> findGroupTaskList(String groupName) {
        //TaskService taskService = taskService;
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(groupName).list();
        System.out.println(groupName + " have " + tasks.size() + " tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ") " + tasks.get(i).getName());
        }
        return tasks;
    }

    private void deleteProcessDefinitionList(List<ProcessDefinition> list) {
        if (list != null && list.size() > 0) {
            for (ProcessDefinition pd : list) {
                // 获取部署ID
                String deploymentId = pd.getDeploymentId();
                //      /*
                //       * 不带级联的删除， 只能删除没有启动的流程，如果流程启动，就会抛出异常
                //       */
                //       processEngine.getRepositoryService().deleteDeployment(deploymentId);

                /**
                 * 级联删除 不管流程是否启动，都可以删除
                 */
                System.out.println("删除部署：" + deploymentId);
                repositoryService.deleteDeployment(deploymentId, true);
            }
        }
    }
}
