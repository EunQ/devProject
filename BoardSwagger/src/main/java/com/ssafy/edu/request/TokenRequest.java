package com.ssafy.edu.request;

import lombok.NoArgsConstructor;
@NoArgsConstructor
public class TokenRequest {
	private String access_token;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
}
