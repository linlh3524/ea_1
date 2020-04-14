package com.xckj.ea;

import com.xckj.ea.activiti.config;
import com.xckj.ea.shiro.CustomRealm;
import org.activiti.engine.RuntimeService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class EaApplicationTests {

	@Test
	void contextLoads() {
		//SecurityManager securityManager=new SecurityManager();


	}
//	@Bean
//	public SecurityManager getSecurityManager(){
//		DefaultWebSecurityManager securityManager =new DefaultWebSecurityManager();
//		securityManager.setRealm(new CustomRealm());
//		return securityManager;
//
//	}

	@Test
	public void startProcess(){

		config c=new config();
		RuntimeService runtimeService=c.getProcessEngine().getRuntimeService();
		String processId="myProcess_1:12:32505";
		runtimeService.startProcessInstanceById(processId);
		System.out.println("success!");


	}


}
