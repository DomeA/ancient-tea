package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TresourceEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TroleEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TuserEntity;

import java.util.List;

public interface TUserService {

    TuserEntity login(String param,String pwd);

    TuserEntity findUserOne(String id);
    TroleEntity findRoleOne(String id);
    TresourceEntity findResourceOne(String id);

    TuserEntity findUserByName(String name);
    TroleEntity findRoleByName(String name);
    TresourceEntity findResourceByName(String name);

    void save(TuserEntity entity);
    void save(TroleEntity troleEntity);
    void save(TresourceEntity tresourceEntity);
    TuserEntity findByNameOrEmailOrPhone(String param);
    List<String> findRoleName(TuserEntity entity);
    Boolean createUser(String name,String pwd,String email,String phone);
    Boolean createRole(String name,String describe);
    Boolean createResource(String name);

    void allocationUserRole(TuserEntity tuserEntity,TroleEntity troleEntity);
    void allocationUserResource(TuserEntity tuserEntity,TresourceEntity tresourceEntity);
    void allocationRoleResource(TroleEntity troleEntity,TresourceEntity tresourceEntity);
}
