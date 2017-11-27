package com.domeastudio.mappingo.servers.microservice.surveying.rest;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.dto.request.Login;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.dto.request.Register;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.pojo.TuserEntity;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.TUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

//@CrossOrigin("*")
@RestController
@RequestMapping(value = "/manager")
public class DataAPI {

    @Autowired
    TUserService tUserService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(@RequestBody Login login){
        TuserEntity t = null;
        t=tUserService.login(login.getUsername(),bCryptPasswordEncoder.encode(login.getPassword()));
        if(t!=null){
            String jwtToken = Jwts.builder().setSubject(t.getUid()).claim("roles", "user").setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
            return jwtToken;
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        return "hello world";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public void addUSer(@RequestBody Register register){
        Boolean f = tUserService.createUser(register.getName(),bCryptPasswordEncoder.encode(register.getPwd()),register.getEmail(),register.getPhone());
        System.out.println("用户："+register.getName()+(f?"成功！":"已经存在"));
    }

    @RequestMapping(value = "/role",method = RequestMethod.POST)
    public void addRole(@RequestParam("name") String name,@RequestParam("describe") String describe){
        Boolean f = tUserService.createRole(name,describe);
        System.out.println("角色："+name+(f?"成功！":"已经存在"));
    }

    @RequestMapping(value = "/resource",method = RequestMethod.POST)
    public void addResource(@RequestParam("name") String name){
        tUserService.createResource(name);
    }

    @RequestMapping(value = "/allocation/{uid}/{rid}",method = RequestMethod.GET)
    public void allocationUserRole(@PathVariable String uid,@PathVariable String rid){
        tUserService.allocationUserRole(tUserService.findUserOne(uid),tUserService.findRoleOne(rid));
    }
}
