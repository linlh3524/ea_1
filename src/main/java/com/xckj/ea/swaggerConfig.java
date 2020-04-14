package com.xckj.ea;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class swaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(("com.xckj.ea")))
                .paths(PathSelectors.any())
                .build();

    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("星才科技审批网站后台API")
                .description("RESTful API文档")
                .termsOfServiceUrl("http://spring.io")
                .version("1.0")
                .build();
    }

}
