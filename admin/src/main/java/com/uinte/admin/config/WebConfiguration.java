package com.uinte.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.uinte.admin.interceptor.UserAuthRestInterceptor;

/**
 * @Auther: admin
 * @Date: 2019/1/21 0021 0:56
 * @Description:
 */

@Configuration()
public class WebConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getUserAuthRestInterceptor()).addPathPatterns("/**")// 拦截所有路径
				.excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/doc.html/**");
	}

	// 注入自定义过滤器
	@Bean
	UserAuthRestInterceptor getUserAuthRestInterceptor() {
		return new UserAuthRestInterceptor();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
