package com.ssafy.manv.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginResponse extends CommonResponse {
	private String accessToken;
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public LoginResponse(int code, String msg) {
		super(code, msg);
	}
}
