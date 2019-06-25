package com.uinte.admin.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // 配置类@Configuration注解，让Spring来加载该类配置
@EnableSwagger2
public class Swagger2 {

	@Bean
	public Docket createRestApi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.apiInfo(apiInfo());
		setGlobalOperationParameters(docket);
		ApiSelectorBuilder apiBuilder = docket.select();
		Predicate<RequestHandler> preList = RequestHandlerSelectors.basePackage("com.uinte.admin.rest");
		apiBuilder.apis(preList);
		apiBuilder.paths(PathSelectors.any());
		apiBuilder.build();
		return docket;
	}
	
	private void setGlobalOperationParameters(Docket docket) {
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization");
        aParameterBuilder.description("token");
        aParameterBuilder.modelRef(new ModelRef("string"));
        aParameterBuilder.parameterType("header");
        aParameterBuilder.required(false);
//        aParameterBuilder.build();
		List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aParameterBuilder.build());
		docket.globalOperationParameters(aParameters);
	}

	private ApiInfo apiInfo() {
		ApiInfoBuilder apiBuilder = new ApiInfoBuilder();
		apiBuilder.title("Swagger2");
		apiBuilder.description("系统接口文档");
		apiBuilder.termsOfServiceUrl("http://www.uinte.com:9002/");
		Contact contact = new Contact("超级管理员", "www.uinte.com", "developer@uinte.com");
		apiBuilder.contact(contact);
		apiBuilder.version("v1.0");
		return apiBuilder.build();
	}

	/**
	 * 注入加密对象
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
