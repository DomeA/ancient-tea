package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.impl;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.MUsersEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.repository.MUsersRepository;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    MUsersRepository mUsersRepository;
    @Override
    public void saveUser() {
        MUsersEntity m=new MUsersEntity();

        m.setName("domea1234");
        m.setId("dddddsq121234234324");
        mUsersRepository.save(m);
    }

    @Override
    public String findByName(String name) {
        MUsersEntity u= mUsersRepository.findByName(name);
        return u.toString();
    }
}
