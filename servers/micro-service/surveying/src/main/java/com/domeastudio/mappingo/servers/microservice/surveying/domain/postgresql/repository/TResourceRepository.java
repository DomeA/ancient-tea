package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.repository;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TresourceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TResourceRepository extends CrudRepository<TresourceEntity,String> {
    TresourceEntity findByName(String name);
}
