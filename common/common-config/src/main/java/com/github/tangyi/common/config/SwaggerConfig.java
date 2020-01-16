package com.github.tangyi.common.config;

import com.github.tangyi.common.security.constant.SecurityConstant;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置
 * <p>
 * 代码描述
 * <p>
 * Copyright: Copyright (C) 2020 XXX, Inc. All rights reserved.
 * <p>
 *
 * @author WQL
 * @since 2020年1月16日 0016 11:53
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(authorizationParameter());
        parameterList.add(tenantCodeParameter());

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any()).build()
            .globalOperationParameters(parameterList);
    }

    /**
     * Authorization 请求头
     *
     * @return
     */
    private Parameter authorizationParameter() {
        ParameterBuilder tokenBuilder = new ParameterBuilder();
        tokenBuilder.name("Authorization").description("Authorization").modelRef(new ModelRef("string"))
            .parameterType("header").required(false).build();
        return tokenBuilder.build();
    }

    /**
     * Tenant-Code 请求头
     *
     * @return
     */
    private Parameter tenantCodeParameter() {
        ParameterBuilder tokenBuilder = new ParameterBuilder();
        tokenBuilder.name("Tenant-Code").defaultValue(SecurityConstant.DEFAULT_TENANT_CODE).description("租户标识")
            .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        return tokenBuilder.build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Swagger API")
            .description("https://gitee.com/wells2333/spring-microservice-exam")
            .termsOfServiceUrl("https://gitee.com/wells2333/spring-microservice-exam")
            .contact(new Contact("tangyi", "https://gitee.com/wells2333/spring-microservice-exam", "1633736729@qq.com"))
            .version("3.4.0").build();
    }

    /**
     * 显示swagger-ui.html文档展示页，还必须注入swagger资源：
     *
     * @param registry
     *            registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
