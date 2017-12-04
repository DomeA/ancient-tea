package com.domeastudio.mappingo.servers.microservice.surveying.rest;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flowable")
public class FlowWorkAPI {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessEngine processEngine;

    @RequestMapping("/task")
    public String task() {
        System.out.println("################################" + taskService);
        return taskService.toString();
    }

    @RequestMapping("/")
    String index() {
        System.out.println("################################taskService" + taskService);
        System.out.println("################################processEngine" + processEngine);
        return "xxxxxxxxxxxxx";
    }
}
