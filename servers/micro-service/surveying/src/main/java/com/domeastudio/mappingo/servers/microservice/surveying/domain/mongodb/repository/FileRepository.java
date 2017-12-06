package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.repository;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.pojo.FileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<FileEntity, String> {
}
