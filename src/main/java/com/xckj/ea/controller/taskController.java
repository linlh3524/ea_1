package com.xckj.ea.controller;

import com.alibaba.fastjson.JSON;
import com.xckj.ea.common.JsonResult;
import com.xckj.ea.common.ResponseCode;

import com.xckj.ea.common.TaskStatus;
import com.xckj.ea.dao.UserMessage;
//import com.xckj.ea.entity.User;
import com.xckj.ea.entity.TaskEntity;
import com.xckj.ea.repository.MessageRepository;
import com.xckj.ea.util.JsonUtil;
import com.xckj.ea.util.ResponseUtil;
import com.xckj.ea.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.List;

@RestController
@Api( tags=" 任务相关接口")
@RequestMapping(value = "/api//task/",produces = MediaType.APPLICATION_JSON_VALUE)
public class taskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private MessageRepository messageRepository;

    private Logger logger= LoggerFactory.getLogger(taskController.class);
    @ApiOperation(value = "获取历史任务表",notes = "根据办理人、任务类型分页展示历史任务表数据")
    @GetMapping(value="/historyTask/list//page/{page}/size/{size}")
    public String historyTask(@ApiParam(value = "页数",required = true) @PathVariable Integer page,
            @ApiParam(value = "条数",required = true) @PathVariable Integer size,
              HttpServletResponse response)
    {
        JsonResult result=new JsonResult();
        Session session=SecurityUtils.getSubject().getSession();
        String assignee= (String) session.getAttribute("userId");
        HistoricTaskInstanceQuery query=historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(assignee)
               // .taskDefinitionKey(type==1?"writerApply":"approve")
                .finished();

        Long count=query.count();
        List<HistoricTaskInstance> list=query.listPage(page-1,size);
        Long totalPage=Math.abs(count/size);
        result.setCode(ResponseCode.SUCCESS.getValue())
                .setContent(list)
                .setTotalPages(totalPage==0?1:totalPage)
                .setSize(count);

        try {
           String resStr = JsonUtil.obj2String(result, new String[]{"resources"});
            ResponseUtil.write(response, resStr);

        } catch (Exception e) {
            result.setCode(ResponseCode.ERROR.getValue())
                    .setContent(Collections.emptyList());
            try {
                ResponseUtil.write(response, JsonUtil.obj2String(result));
            } catch (Exception ex) {
                logger.info(String.valueOf(ex.getCause()));
            }

        }



        return null;


    }
    @ApiOperation(value = "获取待办任务表",notes="分页显示代办任务信息")
    @GetMapping(value = "/todoTask/page/{page}/size/{size}")
    public  JsonResult todoTask(
//            @ApiParam(value = "办理人",required = true)
//            @PathVariable String assinee,
            @ApiParam(value = "页数",required = true)
            @PathVariable Integer page,
            @ApiParam(value = "条数",required = true) @PathVariable Integer size,
            HttpServletResponse response)
    {
     //   User user = (User) SecurityUtils.getSubject().getPrincipal();
        Session session=SecurityUtils.getSubject().getSession();
        String assinee= (String) session.getAttribute("userId");

        JsonResult result=new JsonResult();
        TaskQuery  query=taskService.createTaskQuery().taskAssignee(assinee);
        Long count=query.count();
        List<Task> list= query.listPage(page-1,size);
        List<TaskEntity> taskEntities=new ArrayList<>();
        for (Task task : list) {
            TaskEntity entity=new TaskEntity();
            BeanUtils.copyProperties(task, entity);
           // entity.setStatus();

            taskEntities.add(entity);

        }

        try{
            Long totalPage=Math.abs(count/size);
            result.setCode(ResponseCode.SUCCESS.getValue())
                    .setContent(taskEntities)
                    .setTotalPages(totalPage==0?1:totalPage)
                    .setSize(count);
            return result;
        }
        catch (Exception e)
        {
            result.setCode(ResponseCode.ERROR.getValue());
            return result;
        }

//        Long totalPage=Math.abs(count/size);
//        result.setCode(ResponseCode.SUCCESS.getValue())
//                .setContent(list)
//                .setTotalPages(totalPage==0?1:totalPage)
//                .setSize(count);

//        try {
////            ResponseUtil.write(response, result);
////            System.out.println(response);
//
//        } catch (Exception e) {
//            result.setCode(ResponseCode.ERROR.getValue())
//                    .setContent(Collections.emptyList());
//            try {
//                ResponseUtil.write(response, JsonUtil.obj2String(result));
//            } catch (Exception ex) {
//                logger.info(String.valueOf(ex.getCause()));
//            }
//
//        }



    }
    @ApiOperation(value = "执行待办任务",notes = "")
    @PostMapping(value = "/completetask/taskid/{taskid}")
    public  String completeTask(
            @ApiParam(value = "任务ID",required = true)
            @PathVariable String taskid

    )
    {
        JsonResult result=new JsonResult();
        try {
             taskService.complete(taskid);

            result.setCode(ResponseCode.SUCCESS.getValue());

        }catch (Exception e) {
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg(e.getMessage());
        }
        return null;
    }
    @ApiOperation(value = "生成待办消息提醒",notes = "")
    @PostMapping(value = "/taskMessage/assignee/{assignee}")
    public  JsonResult TaskMessage(
            @ApiParam(value = "办理人ID",required = true)
            @PathVariable String assignee

    )
    {
        JsonResult result=new JsonResult();
        try {
            //taskService.complete(taskid);
          //  TaskQuery  query=taskService.createTaskQuery();
            List<UserMessage> userMessages=new ArrayList<>();

            List<Task> tasks =taskService.createNativeTaskQuery()
                    .sql("SELECT ID_,NAME_,ASSIGNEE_ , CREATE_TIME_ FROM " +managementService.getTableName(Task.class)+" where ASSIGNEE_="+assignee)
                    .list();
            for (Task t:tasks){
                UserMessage u=new UserMessage();
                u.setContent(t.getName());
                u.setSendTime(t.getCreateTime());
                u.setUserId(t.getAssignee());
                u.setTheme("处理任务");
                messageRepository.save(u);
                userMessages.add(u);
            }
            userMessages=messageRepository.findAll();
            result.setCode(ResponseCode.SUCCESS.getValue()).setContent(userMessages);
            return result;

        }catch (Exception e) {
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg(e.getMessage());
            return result;
        }


    }



}
