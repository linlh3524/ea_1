package com.xckj.ea.controller;


import com.alibaba.fastjson.JSONObject;
import com.xckj.ea.activiti.config;
import com.xckj.ea.common.JsonResult;
import com.xckj.ea.common.ResponseCode;
import com.xckj.ea.dao.EventType;
import com.xckj.ea.dao.User;
import com.xckj.ea.dao.yingjiEvent;
import com.xckj.ea.entity.DeplDefEntity;
import com.xckj.ea.repository.EventRepository;
import com.xckj.ea.repository.EventTypeRepository;
import com.xckj.ea.util.JsonFileAccess;
import com.xckj.ea.util.JsonUtil;
import com.xckj.ea.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.hibernate.validator.internal.metadata.aggregated.rule.OverridingMethodMustNotAlterParameterConstraints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.zip.ZipInputStream;

@RestController
@Api( tags=" 流程部署相关接口")
@RequestMapping(value = "/api/deployment/",produces = MediaType.APPLICATION_JSON_VALUE)
public class deploymentController
{
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private  RuntimeService runtimeService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventTypeRepository eventTypeRepository;
    private Logger logger= LoggerFactory.getLogger(deploymentController.class);
    @ApiOperation(value="获取流程部署列表",notes="分页显示流程部署信息")
    @GetMapping(value = "/list/page/{page}/size/{size}")
    public String list(@ApiParam(value = "页数",required = true) @PathVariable Integer page,
                       @ApiParam(value = "条数",required = true) @PathVariable Integer size,
                       HttpServletResponse response)
    {
        JsonResult result = new JsonResult();
    //    DeploymentQuery query = repositoryService.createDeploymentQuery();
       ProcessDefinitionQuery query1 =repositoryService.createProcessDefinitionQuery();
       List<ProcessDefinition> list1=query1.latestVersion().listPage(page,size);
        Long count = query1.count();
    //    List<Deployment> deploymentList = query.listPage(page, size);
        Long totalPage = Math.abs(count / size);
        List<DeplDefEntity> deplDefEntities=new ArrayList<>();
        for (ProcessDefinition processDefinition :list1){
            DeplDefEntity deplDefEntity=new DeplDefEntity();
            BeanUtils.copyProperties(processDefinition,deplDefEntity);
            deplDefEntities.add(deplDefEntity);
        }



        result.setCode(ResponseCode.SUCCESS.getValue())
                .setContent(deplDefEntities)
                .setSize(count)
                .setTotalPages(totalPage == 0 ? 1 : totalPage);
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

    @ApiOperation(value="获取事件信息",notes="显示流程事件信息")
    @GetMapping(value = "/event/proDefId/{proDefId}")
    public JsonResult  event(@ApiParam(value = "流程定义ID",required = true) @PathVariable String proDefId,
                             HttpServletResponse response
                        )
    {
        JsonResult result = new JsonResult();

//        try {

            List<yingjiEvent>  event =new ArrayList<>();
            event= eventRepository.findEventByProDefId(proDefId);
            result.setCode(ResponseCode.SUCCESS.getValue())
           .setContent(event);
          //  return result;
//
//            result.setCode(ResponseCode.SUCCESS.getValue())
//                    .setContent(deplDefEntities)
//                    .setSize(count)
//                    .setTotalPages(totalPage == 0 ? 1 : totalPage);
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



//        } catch (Exception e) {
//            result.setCode(ResponseCode.ERROR.getValue())
//                    .setContent(Collections.emptyList());
//            return result;


        //}
      //  return result;



    }

    @ApiOperation(value="获取事件类型信息",notes="显示预设的事件类型信息")
    @GetMapping(value = "/eventType/")
    public JsonResult  eventType( )
    {
        JsonResult result = new JsonResult();

        try {
            List<EventType> eventTypes=eventTypeRepository.findAll();
            result.setCode(ResponseCode.SUCCESS.getValue())
                    .setContent(eventTypes);

            return result;
        }catch (Exception e)
        {
           // event = eventRepository.findEventByProDefId(proDefId);
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg(e.getMessage());
            return result;

        }


    }
    @ApiOperation(value="删除流程部署",notes = "根据流程ID删除流程部署")
    @DeleteMapping(value = "/delete/id/{id}")
    public JsonResult delete( @ApiParam(value = "编号",required = true)
            @PathVariable String id    )
    {
        JsonResult result=new JsonResult();
        try {
            repositoryService.deleteDeployment(id,true);
            result.setCode(ResponseCode.SUCCESS.getValue());

        }catch (Exception e) {
          result.setCode(ResponseCode.ERROR.getValue())
                  .setErrMsg(e.getMessage());
        }
        return result;
    }
    @ApiOperation(value = "新增流程部署",notes="根据ZIP方式部署流程")
    @PostMapping(value = "/insertbyZip")
    public JsonResult insertbyZip(
            @ApiParam(value = "上传的文件",required = true)
            @PathVariable MultipartFile deployFile
            )
    {
        JsonResult result=new JsonResult();
        try {
            Deployment deployment=repositoryService.createDeployment()
                    .name(deployFile.getOriginalFilename())
                    .addZipInputStream(new ZipInputStream((deployFile.getInputStream())))

                    .deploy();
            result.setCode(ResponseCode.SUCCESS.getValue())
                    .setContent(deployment);
        } catch (IOException e) {
          result.setCode(ResponseCode.ERROR.getValue())
        .setErrMsg(e.getMessage());
        }


        return result;


    }

    @ApiOperation(value = "新增流程部署",notes="根据流程文件部署流程")
    @PostMapping(value = "/insertbyFile/bpmnPath/{bpmnPath}")
    public JsonResult insertbyfile(
            @ApiParam(value = "上传的文件名",required = true)
            @PathVariable String bpmnPath
    )
    {
        JsonResult result=new JsonResult();
        try {
//            Deployment deployment=repositoryService.createDeployment()
//                    .name(bpmnPath)
//                    .addClasspathResource("/processes/"+bpmnPath+".bpmn")
//                    .addClasspathResource("/processes/"+bpmnPath+".png")
//                    .deploy();
            InputStream inputStreamBpmn = this.getClass().getResourceAsStream("/processes/"+bpmnPath+".bpmn");
            InputStream inputStreamPng = this.getClass().getResourceAsStream("/processes/"+bpmnPath+".png");
            Deployment deployment = repositoryService.createDeployment()
                    .addInputStream("/processes/"+bpmnPath+".bpmn", inputStreamBpmn)
                    .addInputStream("/processes/"+bpmnPath+".png", inputStreamPng)
                    .deploy();
            result.setCode(ResponseCode.SUCCESS.getValue())
                    .setContent(deployment);
        } catch (Exception e) {
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg(e.getMessage());
        }


        return result;


    }

    @Value("classpath:json/assignee.json")
    Resource resource;
    @ApiOperation(value = "启动流程实例(提交申请后)",notes="根据流程部署ID启动一个流程示例")
    @PostMapping(value="/start/proid/{proid}")
    @ResponseBody
    public JsonResult start(@ApiParam(value = "流程实例ID",required = true) @PathVariable String proid, @ApiParam(value = "关联事件信息",required = true) @RequestBody yingjiEvent  event)
    {

        JsonResult result=new JsonResult();
        try {

            JsonFileAccess jsonFileAccess=new JsonFileAccess();
            JSONObject jsonObject =jsonFileAccess.getjsonFromFile(resource);
            //设置办理人
            Map<String,Object> map=new HashMap<>();
            map=jsonObject.toJavaObject(jsonObject,Map.class);
            //关联事件
            try{
                yingjiEvent ret=eventRepository.save(event);
                result.setCode(ResponseCode.SUCCESS.getValue()).setContent(ret);

            }catch (Exception e)
            {
                result.setCode(ResponseCode.ERROR.getValue()).setErrMsg(e.getMessage());
                return result;

            }
            runtimeService.startProcessInstanceById(proid,map);
            result.setCode(ResponseCode.SUCCESS.getValue());

        } catch (Exception e) {
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg(e.getMessage());
        }


        return result;
    }

    @ApiOperation(value = "保存流程关联事件",notes="将事件存进数据库")
    @PostMapping(value="/eventSave")
    @ResponseBody
    public JsonResult EventSave(  @ApiParam(value = "关联事件信息",required = true) @RequestBody yingjiEvent  event)
    {

        JsonResult result=new JsonResult();
        try {

 //           JsonFileAccess jsonFileAccess=new JsonFileAccess();
        //    JSONObject jsonObject =jsonFileAccess.getjsonFromFile(resource);
//            //设置办理人
//            Map<String,Object> map=new HashMap<>();
//            map=jsonObject.toJavaObject(jsonObject,Map.class);
            //关联事件
           //int evnetId=eventTypeRepository.findById(event.getEventTypeId());


            yingjiEvent ret=eventRepository.save(event);
            result.setCode(ResponseCode.SUCCESS.getValue()).setContent(ret);


        } catch (Exception e) {
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg(e.getMessage());
        }


        return result;
    }








    @ApiOperation(value = "查询流程图",notes="根据流程部署ID查询一个流程图")
    @GetMapping(value="/Image/proid/{proid}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] ImageSerch(@ApiParam(value = "流程实例ID",required = true) @PathVariable String proid)throws IOException
    {


            //runtimeService.startProcessInstanceById(proid);
            InputStream inputStream=repositoryService.getProcessDiagram(proid);
          //  File file=new File("D:/a.png");
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;

//
//            result.setCode(ResponseCode.SUCCESS.getValue()).setContent("查询成功！");
//
//        } catch (Exception e) {
//            result.setCode(ResponseCode.ERROR.getValue())
//                    .setErrMsg(e.getMessage());
//        }



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

}
