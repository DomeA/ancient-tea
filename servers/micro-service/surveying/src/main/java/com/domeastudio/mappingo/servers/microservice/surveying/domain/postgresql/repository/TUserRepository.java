package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.repository;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TuserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TUserRepository extends JpaRepository<TuserEntity,String> {
    TuserEntity findByPhone(String phone);
    TuserEntity findByNameAndPwd(String name,String pwd);
    TuserEntity findByEmailAndPwd(String email,String pwd);
    TuserEntity findByPhoneAndPwd(String phone,String pwd);
    TuserEntity findByName(String name);
    TuserEntity findByEmail(String email);
}
