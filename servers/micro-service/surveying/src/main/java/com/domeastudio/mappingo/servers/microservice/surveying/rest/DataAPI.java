package com.domeastudio.mappingo.servers.microservice.surveying.rest;

import com.domeastudio.mappingo.servers.microservice.surveying.domain.mongodb.services.FileService;
import com.domeastudio.mappingo.servers.microservice.surveying.domain.postgresql.services.TUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("测绘数据管理服务")
@RestController
@RequestMapping("/manager")
public class DataAPI {

    @Autowired
    TUserService tUserService;

    @Autowired
    FileService userService;

    @ApiOperation(value = "用于测试服务是否正常", notes = "", httpMethod = "GET")
    @ApiResponse(code = 200, message = "String", response = String.class)
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "hello world";
    }

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public void addRole(@RequestParam("name") String name, @RequestParam("describe") String describe) {
        Boolean f = tUserService.createRole(name, describe);
        System.out.println("角色：" + name + (f ? "成功！" : "已经存在"));
    }

    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    public void addResource(@RequestParam("name") String name) {
        tUserService.createResource(name);
    }

    @RequestMapping(value = "/allocation/{uid}/{rid}", method = RequestMethod.GET)
    public void allocationUserRole(@PathVariable String uid, @PathVariable String rid) {
        tUserService.allocationUserRole(tUserService.findUserOne(uid), tUserService.findRoleOne(rid));
    }
}
