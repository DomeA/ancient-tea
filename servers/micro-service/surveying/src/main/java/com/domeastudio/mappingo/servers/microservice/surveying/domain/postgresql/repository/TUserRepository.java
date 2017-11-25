package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.repository;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TuserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TUserRepository extends CrudRepository<TuserEntity,String> {
    TuserEntity findByNameOrEmailOrPhone(String param);
    TuserEntity findByNameOrEmailOrPhone(String param,String pwd);
    TuserEntity findByName(String name);
}
