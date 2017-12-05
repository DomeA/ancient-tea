package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.impl;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.BpmnFileEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.repository.BpmnFileRepository;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    BpmnFileRepository mUsersRepository;
    @Override
    public void saveUser() {
        BpmnFileEntity m=new BpmnFileEntity();

        m.setName("domea1234");
        m.setId("dddddsq121234234324");
        mUsersRepository.save(m);
    }

    @Override
    public String findByName(String name) {
        BpmnFileEntity u= mUsersRepository.findByName(name);
        return u.toString();
    }
}
