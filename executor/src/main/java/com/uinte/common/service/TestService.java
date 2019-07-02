package com.uinte.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.github.dadiyang.httpinvoker.annotation.Headers;
import com.github.dadiyang.httpinvoker.annotation.HttpApi;
import com.github.dadiyang.httpinvoker.annotation.HttpReq;
import com.github.dadiyang.httpinvoker.annotation.Param;
import com.uinte.admin.vo.user.JwtAuthenticationRequest;

@HttpApi
public interface TestService {
	
	@HttpReq(value = "http://www.uinte.com:9002/jwt/token", method = "POST", timeout = 50000)
	public List<String> getAllCities(@RequestBody JwtAuthenticationRequest authenticationRequest);
	
	
	@HttpReq("/getCityRest/{id}")
    City getCityWithHeaders(@Param("id") int id, @Headers Map<String, String> headers);
}
