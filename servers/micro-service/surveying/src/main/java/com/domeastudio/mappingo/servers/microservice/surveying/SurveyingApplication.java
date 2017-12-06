package com.domeastudio.mappingo.servers.microservice.surveying;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TroleEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TuserEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SurveyingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyingApplication.class, args);
    }

    //-----------------下面代码处理初始化一个用户-------------
    //用户名:system 用户密码:domea 用户角色:ROLE_SYSADMIN
    //默认角色ROLE_SIGHTSEER
    @Autowired
    TUserService tUserService;

    @Autowired
    public void init() {
        try {
            Boolean uf = tUserService.createUser("system", "domea", "domeastudio@hotmail.com", "18182669306", 999999);
            Boolean rf1 = tUserService.createRole("ROLE_SYSADMIN", "系统管理员角色");
            Boolean rf2 = tUserService.createRole("ROLE_SIGHTSEER", "默认角色,游客角色");

            TuserEntity tuserEntity = tUserService.findUserByName("system");
            TroleEntity troleEntity = tUserService.findRoleByName("ROLE_SYSADMIN");
            Boolean urf = tUserService.allocationUserRole(tuserEntity, troleEntity);

            if (uf) {
                System.out.println("管理员账户：system 创建成功");
                System.out.println("管理员账户clientId：" + tuserEntity.getToken());
            } else {
                System.out.println("管理员账户：system 已经存在");
            }
            if (rf1) {
                System.out.println("系统管理员角色：ROLE_SYSADMIN 创建成功");
            } else {
                System.out.println("系统管理员角色：ROLE_SYSADMIN 已经存在");
            }
            if (rf2) {
                System.out.println("系统管理员角色：ROLE_SIGHTSEER 创建成功");
            } else {
                System.out.println("系统管理员角色：ROLE_SIGHTSEER 已经存在");
            }
            if (urf) {
                System.out.println("管理员账户：[system] 被赋予 系统管理员角色：[ROLE_SYSADMIN] 成功");
            } else {
                System.out.println("管理员账户：[system] 被赋予 系统管理员角色：[ROLE_SYSADMIN] 已经存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
