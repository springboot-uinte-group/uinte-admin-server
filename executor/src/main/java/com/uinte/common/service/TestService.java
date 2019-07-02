package com.uinte.common.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.uinte.common.msg.ObjectRestResponse;
import com.uinte.common.vo.user.JwtAuthenticationRequest;
import com.uinte.httpinvoker.annotation.HttpApi;
import com.uinte.httpinvoker.annotation.HttpReq;

@HttpApi
public interface TestService {
	
	@HttpReq(value = "http://www.uinte.com:9002/jwt/token", method = "POST", timeout = 50000)
	public ObjectRestResponse<String> getToken(@RequestBody JwtAuthenticationRequest authenticationRequest);
	
}
