package com.uinte.common;

import com.uinte.common.msg.ObjectRestResponse;
import com.uinte.common.service.TestService;
import com.uinte.common.vo.user.JwtAuthenticationRequest;
import com.uinte.httpinvoker.HttpApiProxyFactory;

public class MainApp {

	public static void main(String[] args) {
		HttpApiProxyFactory factory = new HttpApiProxyFactory(request -> {
	        // 我们为所有的请求都加上 cookie 和 header
	        request.addHeader("Content-Type", "application/json");
	    });
		TestService testService = factory.getProxy(TestService.class);
		
		JwtAuthenticationRequest obj = new JwtAuthenticationRequest();
		obj.setPassword("admin");
		obj.setUsername("admin");
		
		ObjectRestResponse<String> sss = testService.getToken(obj);
		System.out.println(sss.getData());
		System.out.println(sss.getMessage());
		System.out.println(sss.getStatus());
	}
}
