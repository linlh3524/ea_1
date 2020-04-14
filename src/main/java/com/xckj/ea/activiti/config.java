package com.xckj.ea.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.test.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
public class config {
//    ProcessEngine processEngine=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
//            .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
//            .setJdbcUrl("jdbc:mysql://192.168.184.219:3306/rino?useUnicode=true&characterEncoding=utf-8")
//            .setAsyncExecutorActivate(false)
//            .buildProcessEngine();

//    @Bean
//    public DataSource database() {
//        return DataSourceBuilder.create()
//                .url("jdbc:mysql://192.168.184.219:3306/rino?useUnicode=true&characterEncoding=utf-8")
//                .username("rino_user")
//                .password("123456")
//                .driverClassName("com.mysql.jdbc.Driver")
//                .build();
//    }

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;
    private Logger logger= LoggerFactory.getLogger(config.class);
    @Bean
    //初始化activiti数据库配置
    public ProcessEngineConfiguration getProcessEngineConfiguration() {

        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        processEngineConfiguration.setJdbcUrl(url)
                .setJdbcDriver(driver)
                .setJdbcUsername(user)
                .setJdbcPassword(password)
                .setActivityFontName("宋体")
                .setLabelFontName("宋体")

                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        logger.info("activiti初始化成果。");

        return processEngineConfiguration;


    }
    @Bean
    //启动流程引擎
    public ProcessEngine getProcessEngine(){
        return getProcessEngineConfiguration().buildProcessEngine();
    }

//    @Bean
    //启动流程引擎
//    public RepositoryService getRepositoryService(){
//        RepositoryService repositoryService=getProcessEngine().getRepositoryService();
//        repositoryService.createDeployment()
//                .name("ea1")
//                .category("1")
//                .addClasspathResource("processes/ea1.bpmn")
//                .deploy();
//        return repositoryService;

   // }
//   @Bean
//    public RuntimeService getRuntimeService(){
//
//        config c=new config();
//        RuntimeService runtimeService=c.getProcessEngine().getRuntimeService();
//        String processId="myProcess_1:12:32505";
//        runtimeService.startProcessInstanceById(processId);
//        System.out.println("success!");
//        return runtimeService;
//
//
//    }




}
