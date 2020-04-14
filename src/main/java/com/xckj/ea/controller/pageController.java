package com.xckj.ea.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Api( tags=" 页面跳转操作相关接口")
public class pageController {

    @ApiOperation(value = "用户登录页")
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public  String login()
    {
        return "/login";
    }
    @ApiOperation(value = "用户主页")
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public  String home()
    {
        Subject subject= SecurityUtils.getSubject();
        if( subject.hasRole("1"))
            return "adminHome";
        else
            return "home";

    }


    @ApiOperation(value = "用户管理页")
    @RequestMapping(value = "/admin/userManage",method = RequestMethod.GET)
    public  String userManage()    {

             return "/admin/userManage";

    }

    @ApiOperation(value = "用户消息页")
    @RequestMapping(value = "/user/message",method = RequestMethod.GET)
    public  String userMessage()    {

        return "/user/message";

    }
    @ApiOperation(value = "任务处理页")
    @RequestMapping(value = "user/taskProcess",method = RequestMethod.GET)
    public  String taskProcess(@PathVariable("taskId") String taskId) {

        return "user/taskProcess";

    }
    @ApiOperation(value = "用户任务页")
    @RequestMapping(value = "/user/task",method = RequestMethod.GET)
    public  String userTask()    {
        return "/user/task";

    }
    @ApiOperation(value = "用户个人信息页")
    @RequestMapping(value = "/user/myInfo",method = RequestMethod.GET)
    public  String userMyInfoe()    {

        return "/user/myInfo";

    }

    @ApiOperation(value = "流程管理页")
    @RequestMapping(value = "/admin/processManage",method = RequestMethod.GET)
    public  String processManage()
    {
        return "/admin/processManage";
    }

    @ApiOperation(value = "无操作权限页")
    @RequestMapping(value = "/UnAuth",method = RequestMethod.GET)
    public  String UnAuth()
    {
        return "UnAuth";
    }

    @ApiOperation(value = "七鱼页")
    @RequestMapping(value = "/qiyu/sdkAccessCustom",method = RequestMethod.GET)
    public  String qiyu()
    {
        return "/qiyu/sdkAccessCustom";
    }
    @ApiOperation(value = "提交工单页")
    @RequestMapping(value = "/qiyu/worksheetcreate",method = RequestMethod.GET)
    public  String worksheetcreate()
    {
        return "/qiyu/worksheetcreate";
    }



}
