package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.repository;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.MUsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MUsersRepository extends CrudRepository<MUsersEntity,String> {
}
