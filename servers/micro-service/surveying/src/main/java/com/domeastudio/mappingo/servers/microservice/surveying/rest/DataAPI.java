package com.domeastudio.mappingo.servers.microservice.surveying.rest;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.dto.request.Register;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//@CrossOrigin("*")
@RestController
@RequestMapping("/manager")
public class DataAPI {

    @Autowired
    TUserService tUserService;


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        return "hello world";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public void addUSer(@RequestBody Register register){
        //Boolean f = tUserService.createUser(register.getName(),register.getPwd(),register.getEmail(),register.getPhone());
        String salt="qwertyuiop123456789";
        Boolean f = tUserService.createUser(register.getName(),register.getPwd(),salt,register.getEmail(),register.getPhone());
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
