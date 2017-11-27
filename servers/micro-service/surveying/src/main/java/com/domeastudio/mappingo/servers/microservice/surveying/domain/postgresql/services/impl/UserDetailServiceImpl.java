package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.impl;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TuserEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.repository.TUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    TUserRepository tUserRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        TuserEntity employee = null;
        if(tUserRepository.findByName(s)!=null){
            return new User(employee.getName(), employee.getPwd(), emptyList());
        }
        if(tUserRepository.findByEmail(s)!=null){
            return new User(employee.getName(), employee.getPwd(), emptyList());
        }
        if(tUserRepository.findByPhone(s)!=null){
            return new User(employee.getName(), employee.getPwd(), emptyList());
        }
        return null;
    }
}
