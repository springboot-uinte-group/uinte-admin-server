package com.uinte.common.service;

import java.util.Map;

public interface HttpService<T> {
	public Map<String, Object> parseParams(String obj);
	public Map<String, Object> parseHeader();
	public Map<String, Object> parseMethod();
}
