package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.repository;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.PUsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PUsersRepository extends CrudRepository<PUsersEntity,String> {
}
