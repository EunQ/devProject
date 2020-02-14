package com.ssafy.edu.service;

public interface IJwtService {
	String create(String id, int roles);
	
	String getMemberId(String token);
	
	boolean isUsable(String jwt);
}
