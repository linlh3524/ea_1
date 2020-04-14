package com.xckj.ea.controller;

import com.xckj.ea.common.JsonResult;
import com.xckj.ea.common.ResponseCode;
import com.xckj.ea.dao.Membership;
import com.xckj.ea.dao.User;
import com.xckj.ea.repository.MembershipRespository;
import com.xckj.ea.repository.UserRepository;
import com.xckj.ea.service.MembershipService;
import com.xckj.ea.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.UserQuery;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Pageable;
import java.util.List;

@RestController
@Api( tags=" 用户操作相关接口")
@RequestMapping(value = "/api/user/",produces = MediaType.APPLICATION_JSON_VALUE)
public class userController {

    @Autowired
    UserRepository userRepository;
    @Autowired
   MembershipRespository membershipRespository;
    @Autowired
    IdentityService identityService;

    @ApiOperation(value = "用户登录",notes="根据用户名、用户密码登录")
    @GetMapping(value = "/tologin/userid/{userid}/pwd/{pwd}")
    public JsonResult tologin(
            @ApiParam(value = "用户名",required = true)
            @PathVariable String userid,
            @ApiParam(value = "密码",required = true) @PathVariable String pwd,
            HttpServletResponse response

    )
    {


        JsonResult result=new JsonResult();

             Subject subject= SecurityUtils.getSubject();
             UsernamePasswordToken token=new UsernamePasswordToken(userid,pwd);
        try{
            subject.login(token);
            Session curentUser=subject.getSession();
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            curentUser.setAttribute("userId",user.getId());
            curentUser.setAttribute("userName",user.getUserName());




            result.setCode(ResponseCode.SUCCESS.getValue());
           // ResponseUtil.write(response, result);
            return result;

        }catch (UnknownAccountException e)
        {
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg("用户名不存在");
            return result;
        }
        catch (IncorrectCredentialsException ie){
            result.setCode(ResponseCode.ERROR.getValue())
                    .setErrMsg("密码不正确");
            return result;
        }
        catch (Exception e)
        {result.setCode(ResponseCode.ERROR.getValue())
                .setErrMsg(e.getMessage());
        return result;
        }




       // return null;
    }



    @ApiOperation(value = "用户列表",notes="获取用户信息")
    @GetMapping(value = "/userList")
    public JsonResult userList( HttpServletResponse response)
    {
        JsonResult result=new JsonResult();
        try{

            List<User>  list=userRepository.findAll();
            result.setContent(list).setCode(ResponseCode.SUCCESS.getValue());
            return result;

        }
        catch (Exception e)
        {result.setCode(ResponseCode.ERROR.getValue())
                .setErrMsg(e.getMessage());
            return result;
        }

    }

    @ApiOperation(value = "组列表",notes="获取组信息")
    @GetMapping(value = "/groupList")
    public JsonResult groupList( HttpServletResponse response)
    {
        JsonResult result=new JsonResult();
        try{
            List<Group> group= identityService.createGroupQuery().list();
            result.setContent(group).setCode(ResponseCode.SUCCESS.getValue());
            return result;
        }
        catch (Exception e)
        {result.setCode(ResponseCode.ERROR.getValue())
                .setErrMsg(e.getMessage());
            return result;
        }
    }


    @ApiOperation(value = "用户新增",notes="添加一个用户(group_id须在组列表内选择)")
    @PostMapping(value = "/userSave")
    @ResponseBody
    public JsonResult userSave(  @ApiParam(value = "用户信息",required = true) @RequestBody User user )
    {
        JsonResult result=new JsonResult();
        try{
           UserQuery query =identityService.createUserQuery().userId(user.getId());
           if(query.list().isEmpty())
           {
               if(!identityService.createGroupQuery().groupId(user.getGroup_id()).list().isEmpty())
               {
                   user=userRepository.save(user);
                   identityService.createMembership(user.getId(),user.getGroup_id());

                   result.setContent(user).setCode(ResponseCode.SUCCESS.getValue());
                   return result;
               }
               else
               {

                   result.setCode(ResponseCode.ERROR.getValue())
                           .setErrMsg("用户组ID不存在！");
                   return result;
               }


           }
           else
           {
               result.setCode(ResponseCode.ERROR.getValue())
                       .setErrMsg("用户名已存在！");
               return result;
           }




        }
        catch (Exception e)
        {result.setCode(ResponseCode.ERROR.getValue())
                .setErrMsg(e.getMessage());
            return result;
        }




        // return null;
    }


    @ApiOperation(value = "用户删除",notes="根据用户ID删除用户信息")
    @PostMapping(value = "/userDelete/id/{id}")

    public JsonResult userDelete(  @ApiParam(value = "用户名",required = true) @PathVariable String id )
    {
        JsonResult result=new JsonResult();
        try{
            User deleteUser=new User();
           // identityService.
            identityService.deleteMembership("","");
           Membership membership= membershipRespository.findMembershipById(id);
            deleteUser=userRepository.findUserById(id);


            identityService.deleteMembership(membership.getUserId(),membership.getGroupId());
            userRepository.deleteById(id);

            result.setContent(deleteUser).setCode(ResponseCode.SUCCESS.getValue());
            return result;

        }
        catch (Exception e)
        {result.setCode(ResponseCode.ERROR.getValue())
                .setErrMsg(e.getMessage());
            return result;
        }




        // return null;
    }

    @ApiOperation(value = "密码重置",notes="根据用户ID重置用户密码(初始化为“123456”)")
    @PostMapping(value = "/passwordReset/id/{id}")

    public JsonResult passwordReset(  @ApiParam(value = "用户名",required = true) @PathVariable String id
    )
    {
        JsonResult result=new JsonResult();
        try{


            if(userRepository.findUserById(id)!=null)
            {

                if(userRepository.UpdatePwd(id,"123456")!=-1)

                   result.setContent(userRepository.findUserById(id)).setCode(ResponseCode.SUCCESS.getValue());
                else
                    result.setCode(ResponseCode.ERROR.getValue()).setErrMsg("修改失败！");

                return result;

            }
            else
            {
                result.setCode(ResponseCode.ERROR.getValue())
                        .setErrMsg("用户不存在！");
                return result;
            }



        }
        catch (Exception e)
        {   result.setCode(ResponseCode.ERROR.getValue())
                .setErrMsg(e.getMessage());
            return result;
        }




        // return null;
    }
}
