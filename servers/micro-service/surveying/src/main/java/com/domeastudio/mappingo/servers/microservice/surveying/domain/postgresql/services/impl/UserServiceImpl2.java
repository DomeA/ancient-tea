package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.impl;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.PUsersEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.repository.PUsersRepository;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.UserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "transactionManagerSecondary")
public class UserServiceImpl2 implements UserService2 {
    @Autowired
    PUsersRepository pUsersRepository;

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class})
    public void saveUser() {
        PUsersEntity p=new PUsersEntity();
        p.setName("pdomea1111");
        p.setPwd("ddd11");
        pUsersRepository.save(p);
    }
}
