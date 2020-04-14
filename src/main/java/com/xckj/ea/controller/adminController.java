package com.xckj.ea.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api( tags=" 管理员操作相关接口")
@RequestMapping(value = "/api/user/",produces = MediaType.APPLICATION_JSON_VALUE)
public class adminController {


    @ApiOperation(value = "用户管理",notes="对用户进行增删改操作")
    @GetMapping(value = "admin/userManage")
    public  String login( )
    {        return "userManage";
    }



}

