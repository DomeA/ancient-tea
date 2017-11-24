package com.domeastudio.mappingo.servers.microservice.surveying.rest;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.MUsersEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.repository.MUsersRepository;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.PUsersEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.repository.PUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class DataAPI {

    @Autowired
    MUsersRepository mUsersRepository;

    @Autowired
    PUsersRepository pUsersRepository;

    @RequestMapping(value = "/addPUser",method = RequestMethod.GET)
    public void addPostgresqlUSer(){
        PUsersEntity p=new PUsersEntity();

        p.setName("pdomea");
        p.setPwd("ddd");
        pUsersRepository.save(p);
    }
    @RequestMapping(value = "/addMUser",method = RequestMethod.GET)
    public void addMongodbUSer(){
        MUsersEntity m=new MUsersEntity();

        m.setName("mdomea");
        m.setPwd("ddcc");
        mUsersRepository.save(m);
    }
}
