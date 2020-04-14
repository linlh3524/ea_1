package com.xckj.ea.shiro;

import com.xckj.ea.dao.User;
import com.xckj.ea.repository.UserRepository;
import com.xckj.ea.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Optional;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
      System.out.println("执行认证");
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo();
        String id=(String) authenticationToken.getPrincipal();
        String pwd=new String((char[])authenticationToken.getCredentials());
        User user= userService.findById(id);
      //  System.out.println(user.getId());
        if(user==null||!user.getId().equals(id)){
            throw new UnknownAccountException();
        }
        else if(!user.getPwd().equals(pwd))
            throw new IncorrectCredentialsException();
        else{



            return new SimpleAuthenticationInfo(user,pwd,getName());

        }



    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("执行授权");
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        Subject subject= SecurityUtils.getSubject();
        User currentUser=(User) subject.getPrincipal();
       // info.addStringPermission(currentUser.getGroup_id());
        info.addRole(currentUser.getGroup_id());

        return info;
    }
}
