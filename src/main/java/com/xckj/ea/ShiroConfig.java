package com.xckj.ea;

import com.xckj.ea.shiro.CustomRealm;
import com.xckj.ea.shiro.PasswordHelper;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("SecurityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean =new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String,String> filterChainDefinitonMap=new LinkedHashMap<String,String>();
        DefaultFilterChainManager filterChainManager=new DefaultFilterChainManager();

        filterChainDefinitonMap.put("/login","anon");
        filterChainDefinitonMap.put("/css/**","anon");
       // filterChainDefinitonMap.put("/js/**","anon");
        filterChainDefinitonMap.put("/admin/**","roles[1]");
        filterChainDefinitonMap.put("/user/**","roles[2]");
        filterChainDefinitonMap.put("/home","authc");
        filterChainDefinitonMap.put("/adminHome","authc");
      //  filterChainDefinitonMap.put("/**", "authc");
        filterChainDefinitonMap.put("/js/**","anon");

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/home");
        shiroFilterFactoryBean.setUnauthorizedUrl("/UnAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitonMap);
        return shiroFilterFactoryBean;


    }
    @Bean(name="SecurityManager")
    public SecurityManager getSecurityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;



    }


    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME); // 散列算法
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.HASH_ITERATION); // 散列次数
        return hashedCredentialsMatcher;
    }

    @Bean
    public CustomRealm shiroRealm() {
        CustomRealm shiroRealm = new CustomRealm();
    //    shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher()); // 原来在这里
        return shiroRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public PasswordHelper passwordHelper() {
        return new PasswordHelper();
    }
//    @Bean
//    public LogoutFilter logoutFilter()
//    {
//         LogoutFilter logoutFilter=new LogoutFilter();
//         logoutFilter.setRedirectUrl("/login");
//         return logoutFilter;
//    }
//    @Bean
//    public RememberMeManager rememberMeManager(){
//        RememberMeManager rememberMeManager =new RememberMeManager();
//
//    }
    @Bean
    public CookieRememberMeManager cookieRememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager=new CookieRememberMeManager();
        SimpleCookie simpleCookie=new SimpleCookie();
        simpleCookie.setMaxAge(604800);
        simpleCookie.setName("rememberMe");
        cookieRememberMeManager.setCookie(simpleCookie);
        return  cookieRememberMeManager;
    }
}
