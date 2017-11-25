package com.domeastudio.mappingo.servers.microservice.surveying.rest;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.dto.request.Login;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.dto.request.Register;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TuserEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/manager")
public class DataAPI {

    @Autowired
    TUserService tUserService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void login(@RequestBody Login login){
        TuserEntity t = null;
        t=tUserService.login(login.getName(),login.getPwd());
        if(t!=null){

        }else{

        }
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public void addUSer(@RequestBody Register register){
        tUserService.createUser(register.getName(),register.getPwd(),register.getEmail(),register.getPhone());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/role",method = RequestMethod.POST)
    public void addRole(@RequestParam("name") String name){
        tUserService.createRole(name);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/resource",method = RequestMethod.POST)
    public void addResource(@RequestParam("name") String name){
        tUserService.createResource(name);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/allocation/{uid}/{rid}",method = RequestMethod.GET)
    public void allocationUserRole(@PathVariable String uid,@PathVariable String rid){
        tUserService.allocationUserRole(tUserService.findUserOne(uid),tUserService.findRoleOne(rid));
    }
}
