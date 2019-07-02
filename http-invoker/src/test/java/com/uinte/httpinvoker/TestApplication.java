package com.uinte.httpinvoker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.uinte.httpinvoker.annotation.HttpApiScan;
import com.uinte.httpinvoker.mocker.MockRequestor;
import com.uinte.httpinvoker.mocker.MockResponse;
import com.uinte.httpinvoker.mocker.MockRule;
import com.uinte.httpinvoker.requestor.HttpRequest;
import com.uinte.httpinvoker.requestor.RequestPreprocessor;

import java.util.Collections;

@Configuration
@HttpApiScan(configPaths = "classpath:conf.properties")
@PropertySource("classpath:conf2.properties")
public class TestApplication {

    @Bean
    public RequestPreprocessor requestPreprocessor() {
        return new RequestPreprocessor() {
            @Override
            public void process(HttpRequest request) {
                request.addHeader("testHeader", "OK");
                request.addCookie("testCookie", "OK");
            }
        };
    }

    @Bean
    public MockRequestor requestor() {
        MockRequestor requestor = new MockRequestor();
        MockRule rule = new MockRule("http://localhost:18888/city/getCityName", Collections.singletonMap("id", (Object) 1), new MockResponse(200, "北京"));
        requestor.addRule(rule);
        return requestor;
    }
}
