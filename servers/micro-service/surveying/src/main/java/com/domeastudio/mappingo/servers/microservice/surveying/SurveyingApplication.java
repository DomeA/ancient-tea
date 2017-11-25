package com.domeastudio.mappingo.servers.microservice.surveying;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TuserEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SurveyingApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SurveyingApplication.class, args);
    }
    //-----------------下面代码处理初始化一个用户-------------
    //用户名:system 用户密码:domea 用户角色:ROLE_SYSADMIN
    //默认角色ROLE_SIGHTSEER
    @Autowired
    TUserService tUserService;

    @Autowired
    public void init(){
        try {
            tUserService.createUser("system","domea","domeastudio@hotmail.com","18182669306");
            tUserService.createRole("ROLE_SYSADMIN");
            tUserService.createRole("ROLE_SIGHTSEER");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
