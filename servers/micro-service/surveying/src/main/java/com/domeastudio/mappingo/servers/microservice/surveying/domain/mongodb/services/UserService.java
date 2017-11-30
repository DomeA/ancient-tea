package com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services;

public interface UserService {
    void saveUser();
    String findByName(String name);
}
