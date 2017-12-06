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
        entityManagerFactoryRef = "entityManagerFactoryThird",
        transactionManagerRef = "transactionManagerThird",
        basePackages = {"com.domeastudio.mappingo.servers.microservice.surveying.domain.basedata.repository"})
//设置Repository所在位置
public class ThirdConfig {
    @Autowired
    @Qualifier("thirdDataSource")
    private DataSource thirdDataSource;

    @Bean(name = "entityManagerThird")
    public EntityManager entityManager() {
        return entityManagerFactoryThird().getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryThird")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryThird() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        jpaVendorAdapter.setShowSql(true);

        jpaVendorAdapter.setDatabasePlatform("org.hibernate.spatial.dialect.postgis.PostgisDialect");

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(thirdDataSource);
        factoryBean.setPackagesToScan("com.domeastudio.mappingo.servers.microservice.surveying.domain.basedata.pojo");
        factoryBean.setPersistenceUnitName("thirdPersistenceUnit");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setJpaPropertyMap(jpaProperties());
        return factoryBean;
    }

    private Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.ejb.naming_strategy", new SpringNamingStrategy());
        return props;
    }

    @Bean(name = "transactionManagerThird")
    public PlatformTransactionManager transactionManagerThird() {
        return new JpaTransactionManager(entityManagerFactoryThird().getObject());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(thirdDataSource);
    }

    @Bean("transactionTemplateThird")
    public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }
}
