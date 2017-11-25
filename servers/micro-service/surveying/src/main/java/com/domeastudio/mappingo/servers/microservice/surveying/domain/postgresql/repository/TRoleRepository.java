package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.repository;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TroleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TRoleRepository extends CrudRepository<TroleEntity,String> {
    TroleEntity findByName(String name);
}
