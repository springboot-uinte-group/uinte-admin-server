package com.uinte.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uinte.common.handler.GlobalExceptionHandler;

/**
 * 将GlobalExceptionHandler全局异常拦截器 加入bean ioc中
 */
@Configuration
public class AuthConfiguration {
    @Bean
    public GlobalExceptionHandler getGlobalExceptionHandler(){
        return new GlobalExceptionHandler();
    }
}
