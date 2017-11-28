package com.domeastudio.mappingo.servers.microservice.surveying;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.TUserService;
import com.domeastudio.mappingo.servers.microservice.surveying.util.MD5Utils;
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
            String salt="qwertyuiop123456789";

            Boolean uf=tUserService.createUser("system", MD5Utils.getMD5("domea"+salt),salt,"domeastudio@hotmail.com","18182669306");
            Boolean rf1=tUserService.createRole("ROLE_SYSADMIN","系统管理员角色");
            Boolean rf2=tUserService.createRole("ROLE_SIGHTSEER","默认角色,游客角色");
            if(uf){
                System.out.println("管理员账户：system 创建成功");
            }else{
                System.out.println("管理员账户：system 已经存在");
            }
            if(rf1){
                System.out.println("系统管理员角色：ROLE_SYSADMIN 创建成功");
            }else{
                System.out.println("系统管理员角色：ROLE_SYSADMIN 已经存在");
            }
            if(rf2){
                System.out.println("系统管理员角色：ROLE_SIGHTSEER 创建成功");
            }else{
                System.out.println("系统管理员角色：ROLE_SIGHTSEER 已经存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
