package com.uinte.admin.config;

/**
 * @Date: 2019/1/14 0014 23:51
 * @Description:
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // 配置类@Configuration注解，让Spring来加载该类配置
@EnableSwagger2
public class Swagger2 {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.uinte.admin.rest")).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		ApiInfoBuilder apiBuilder = new ApiInfoBuilder();
		apiBuilder.title("restful 接口文档");
		apiBuilder.description("系统开发接口文档");
		apiBuilder.termsOfServiceUrl("http://localhost:8999/");
		Contact contact = new Contact("超级管理员", "www.uinte.com","developer@uinte.com");
		apiBuilder.contact(contact);
		apiBuilder.version("1.0");
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
