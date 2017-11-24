package com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RUserResourceRepository extends CrudRepository<RUserResourceRepository,String> {
}
