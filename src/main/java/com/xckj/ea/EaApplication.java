package com.xckj.ea;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication(exclude = {org.activiti.spring.boot.SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class EaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EaApplication.class, args);
	}


}
