package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.impl;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.MUsersEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.repository.MUsersRepository;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(value = "transactionManagerSecondary")
public class UserServiceImpl implements UserService {
    @Autowired
    MUsersRepository mUsersRepository;
    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class})
    public void saveUser() {
        MUsersEntity m=new MUsersEntity();

        m.setName("mdomea11111");
        m.setPwd("ddcc111");
        mUsersRepository.save(m);
    }
}
