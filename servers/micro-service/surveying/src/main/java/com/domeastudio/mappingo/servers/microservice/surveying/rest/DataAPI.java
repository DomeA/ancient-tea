package com.domeastudio.mappingo.servers.microservice.surveying.rest;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.MUsersEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.repository.MUsersRepository;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.UserService;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.PUsersEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.repository.PUsersRepository;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.UserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class DataAPI {

    @Autowired
    UserService userService;

    @Autowired
    UserService2 userService2;

    @RequestMapping(value = "/addPUser",method = RequestMethod.GET)
    public void addPostgresqlUSer(){
        userService2.saveUser();
    }
    @RequestMapping(value = "/addMUser",method = RequestMethod.GET)
    public void addMongodbUSer(){
        userService.saveUser();

    }
}
