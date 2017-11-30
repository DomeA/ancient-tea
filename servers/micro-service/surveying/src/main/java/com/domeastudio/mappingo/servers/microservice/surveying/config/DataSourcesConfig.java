package com.domeastudio.mappingo.servers.microservice.surveying.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourcesConfig {

    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryDataSource")
    @Qualifier("secondaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "thirdDataSource")
    @Qualifier("thirdDataSource")
    @ConfigurationProperties(prefix="spring.datasource.third")
    public DataSource thirdDataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean(name = "fourthDataSource")
//    @Qualifier("fourthDataSource")
//    @ConfigurationProperties(prefix="spring.datasource.fourth")
//    public DataSource fourthDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//    @Bean(name="fourthMongoProperties")
//    @ConfigurationProperties(prefix="spring.datasource.fourth")
//    public MongoProperties fourthDataSource() {
//        return new MongoProperties();
//    }
}
