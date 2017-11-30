package com.domeastudio.mappingo.servers.microservice.surveying.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryFourth",
        transactionManagerRef = "transactionManagerFourth",
        basePackages = {"com.domeastudio.mappingo.servers.microservice.surveying.domain.oracle.repository"})
//设置Repository所在位置
public class FourthConfig {
    @Autowired
    @Qualifier("fourthDataSource")
    private DataSource fourthDataSource;

    @Bean(name = "entityManagerFourth")
    public EntityManager entityManager() {
        return entityManagerFactoryFourth().getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryFourth")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryFourth() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabase(Database.ORACLE);
        jpaVendorAdapter.setShowSql(true);

        //org.hibernate.spatial.dialect.oracle.OracleSpatial10gDialect
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.Oracle10gDialect");

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(fourthDataSource);
        factoryBean.setPackagesToScan("com.domeastudio.mappingo.servers.microservice.surveying.domain.oracle.pojo");
        factoryBean.setPersistenceUnitName("fourthPersistenceUnit");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setJpaPropertyMap(jpaProperties());
        return factoryBean;
    }

    private Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.ejb.naming_strategy", new SpringNamingStrategy());
        return props;
    }

    @Bean(name = "transactionManagerFourth")
    public PlatformTransactionManager transactionManagerFourth() {
        return new JpaTransactionManager(entityManagerFactoryFourth().getObject());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(fourthDataSource);
    }

    @Bean("transactionTemplateFourth")
    public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager){
        return new TransactionTemplate(platformTransactionManager);
    }
}
