package com.xckj.ea.controller;

import com.alibaba.fastjson.JSONObject;
import com.xckj.ea.common.JsonResult;
import com.xckj.ea.common.ResponseCode;
import com.xckj.ea.dao.User;
import com.xckj.ea.dao.UserMessage;
import com.xckj.ea.repository.DepartRepository;
import com.xckj.ea.repository.MessageRepository;
import com.xckj.ea.service.MessageService;
import com.xckj.ea.shiro.PasswordHelper;
import com.xckj.ea.util.JsonFileAccess;
import com.xckj.ea.util.qiyu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sun.misc.IOUtils;

import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api( tags=" 通用接口")
@RequestMapping(value = "/api/test/",produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonController {


    @Autowired
    MessageRepository messageRepository;
    @Autowired
    qiyu  q;

    @ApiOperation(value = "密码加密测试",notes="")
    @GetMapping(value = "/pwdtest/pwd/{pwd}")
    public  JsonResult login(
            @ApiParam(value = "密码",required = true) @PathVariable String pwd

    )
    {
        JsonResult result=new JsonResult();
        try {
            PasswordHelper p= new PasswordHelper();
            User user=new User();
            user.setPwd(pwd);
            p.encrytPassword(user);


            result.setCode(ResponseCode.SUCCESS.getValue()).setContent(user.getPwd());

        } catch (Exception e) {
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg(e.getMessage());
        }
        return result;
    }


    @ApiOperation(value = "发送消息",notes="")
    @ResponseBody
    @PostMapping(value = "/postMessage")
    public  JsonResult PostMessage(@ApiParam(value = "用户消息",required = true) @RequestBody UserMessage message

    )
    {
        JsonResult result=new JsonResult();
        try {

             message=messageRepository.save(message);
            result.setCode(ResponseCode.SUCCESS.getValue()).setContent(message);

        } catch (Exception e) {
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg(e.getMessage());
        }
        return result;
    }

    @Value("classpath:json/assignee.json")
    Resource resource;
    @ApiOperation(value = "获取json文件数据",notes="")
    @GetMapping(value = "/json")
    public  JsonResult json()
    {
        JsonResult result=new JsonResult(

        );
        try {

            JsonFileAccess jsonFileAccess=new JsonFileAccess();
            JSONObject jsonObject =jsonFileAccess.getjsonFromFile(resource);
            JSONObject s=jsonObject.getJSONObject("assignee");


            result.setCode(ResponseCode.SUCCESS.getValue()).setContent(s);

        } catch (Exception e) {
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "计算校验和",notes="")
    @PostMapping(value = "/checksum")
    @ResponseBody
    public  JsonResult checksum(  @ApiParam(value = "json字符串",required = true) @RequestBody  JSONObject json
                              )
    {
        JsonResult result=new JsonResult();
        try {

            String appSercet="linlihong";
            Date dt= new Date();
            String jsonStr=json.toJSONString();
            StringBuffer buf = new StringBuffer();
           try {
               MessageDigest md = MessageDigest.getInstance("MD5");
               md.update(jsonStr.getBytes());

               byte[] bits = md.digest();
               for (int i = 0; i < bits.length; i++) {
                   int a = bits[i];
                   if (a < 0) a += 256;
                   if (a < 16) buf.append("0");
                   buf.append(Integer.toHexString(a));
               }
           }catch (Exception e)
           {

           }

            JSONObject ret=new JSONObject();
           ret.put("dt",dt);
           ret.put("checksum", q.encode(appSercet,buf.toString().toLowerCase(),dt));




            result.setCode(ResponseCode.SUCCESS.getValue()).setContent(ret);

        } catch (Exception e) {
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg(e.getMessage());
        }
        return result;
    }

    @GetMapping(value = "/image",produces = MediaType.IMAGE_JPEG_VALUE )
    @ResponseBody
    public byte[] getImage() throws IOException {
        File file = new File("D:/test.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    @Autowired
    MessageService messageService;
    @ApiOperation(value = "生成消息",notes="")
    @PostMapping(value = "/generateMessage")
    @ResponseBody
    public  JsonResult generateMessage( )
    {
        JsonResult result=new JsonResult();
        try {

           messageService.generateMessage();
           result.setCode(ResponseCode.SUCCESS.getValue());

        } catch (Exception e) {
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg(e.getMessage());
        }
        return result;
    }


















    @ApiOperation(value = "手动生成流程图测试",notes="")
    @GetMapping(value = "/newProcess")
    public  JsonResult newDeployment(
    )
    {
        JsonResult result=new JsonResult();
        try {
            // 1. Build up the model from scratch
            BpmnModel model = new BpmnModel();
            Process process = new Process();
            model.addProcess(process);
            final String PROCESSID = "process01";
            final String PROCESSNAME = "测试01";
            process.setId(PROCESSID);
            process.setName(PROCESSNAME);
            process.addFlowElement(createStartEvent());
            process.addFlowElement(createUserTask("task1", "节点01", "candidateGroup1"));
            process.addFlowElement(createExclusiveGateway("createExclusiveGateway1"));
            process.addFlowElement(createUserTask("task2", "节点02", "candidateGroup2"));
            process.addFlowElement(createExclusiveGateway("createExclusiveGateway2"));
            process.addFlowElement(createUserTask("task3", "节点03", "candidateGroup3"));
            process.addFlowElement(createExclusiveGateway("createExclusiveGateway3"));
            process.addFlowElement(createUserTask("task4", "节点04", "candidateGroup4"));
            process.addFlowElement(createEndEvent());

            process.addFlowElement(createSequenceFlow("startEvent", "task1", "", ""));
            process.addFlowElement(createSequenceFlow("task1", "task2", "", ""));
            process.addFlowElement(createSequenceFlow("task2", "createExclusiveGateway1", "", ""));
            process.addFlowElement(createSequenceFlow("createExclusiveGateway1", "task1", "不通过", "${pass=='2'}"));
            process.addFlowElement(createSequenceFlow("createExclusiveGateway1", "task3", "通过", "${pass=='1'}"));
            process.addFlowElement(createSequenceFlow("task3", "createExclusiveGateway2", "", ""));
            process.addFlowElement(createSequenceFlow("createExclusiveGateway2", "task2", "不通过", "${pass=='2'}"));
            process.addFlowElement(createSequenceFlow("createExclusiveGateway2", "task4", "通过", "${pass=='1'}"));
            process.addFlowElement(createSequenceFlow("task4", "createExclusiveGateway3", "", ""));
            process.addFlowElement(createSequenceFlow("createExclusiveGateway3", "task3", "不通过", "${pass=='2'}"));
            process.addFlowElement(createSequenceFlow("createExclusiveGateway3", "endEvent", "通过", "${pass=='1'}"));


            BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

            byte[] convertToXML = bpmnXMLConverter.convertToXML(model);

            String bytes = new String(convertToXML);


            System.out.println(bytes);

            result.setCode(ResponseCode.SUCCESS.getValue());
        }catch (Exception e){
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg(e.getMessage());
        }
        return result;

         //    2. Generate graphical information

          //   3. Deploy the process to the engine
//            Deployment deployment = repositoryService.createDeployment().addBpmnModel(PROCESSID+".bpmn", model).name(PROCESSID+"_deployment").deploy();
//
//            // 4. Start a process instance
//            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESSID);
//
//           //  5. Check if task is available
//            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
//           // Assert.assertEquals(1, tasks.size());
//
//            // 6. Save process diagram to a file
//           // InputStream processDiagram = repositoryService.getProcessDiagram();
//            InputStream inputStream=repositoryService.getProcessDiagram(processInstance.getProcessDefinitionId());
//            File file=new File("D:/a.png");
//            try {
//                BufferedOutputStream outputStream=new BufferedOutputStream(new FileOutputStream(file));
//                int len=0;
//                byte[] b=new byte[1024];
//                while((len=inputStream.read(b))!=-1) {
//                    outputStream.write(b, 0, len);
//                    outputStream.flush();
//                }
//                outputStream.close();
//                inputStream.close();
//                System.out.println("获取图片成功");
//            }catch (Exception e){}
//
//            // 7. Save resulting BPMN xml to a file
//            InputStream processBpmn =repositoryService.getResourceAsStream(deployment.getId(), PROCESSID+".bpmn");
//            FileUtils.copyInputStreamToFile(processBpmn,new File("D:/deployments/"+PROCESSID+".bpmn"));
//
//
//            System.out.println(".........end...");
//
//
//
//
//        result.setCode(ResponseCode.SUCCESS.getValue()) ;
//
//        } catch (Exception e) {
//            result.setCode(ResponseCode.ERROR.getValue())
//                    .setErrMsg(e.getMessage());
//        }
//        return null;
    }


    /*任务节点*/
    protected static UserTask createUserTask(String id, String name, String candidateGroup) {
        List<String> candidateGroups=new ArrayList<String>();
        candidateGroups.add(candidateGroup);
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setCandidateGroups(candidateGroups);
        return userTask;
    }

    /*连线*/
    protected static SequenceFlow createSequenceFlow(String from, String to,String name,String conditionExpression) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        flow.setName(name);
        if(StringUtils.isNotEmpty(conditionExpression)){
            flow.setConditionExpression(conditionExpression);
        }
        return flow;
    }

    /*排他网关*/
    protected static ExclusiveGateway createExclusiveGateway(String id) {
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId(id);
        return exclusiveGateway;
    }

    /*开始节点*/
    protected static StartEvent createStartEvent() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("startEvent");
        return startEvent;
    }

    /*结束节点*/
    protected static EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent");
        return endEvent;
    }




}
