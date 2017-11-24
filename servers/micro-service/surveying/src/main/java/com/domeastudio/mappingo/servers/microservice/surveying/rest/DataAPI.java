package com.domeastudio.mappingo.servers.microservice.surveying.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class DataAPI {


    @RequestMapping(value = "/addPUser",method = RequestMethod.GET)
    public void addPostgresqlUSer(){
        //userService2.saveUser();
    }
    @RequestMapping(value = "/addMUser",method = RequestMethod.GET)
    public void addMongodbUSer(){
        //userService.saveUser();

    }
}
