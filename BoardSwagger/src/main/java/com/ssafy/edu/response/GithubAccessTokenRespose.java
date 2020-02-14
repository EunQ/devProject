package com.ssafy.edu.response;

public class GithubAccessTokenRespose {
	private String access_token;
	private String scope;
	private String token_type;
	private String login_access_token;
	
	public GithubAccessTokenRespose() {
		super();
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getLogin_access_token() {
		return login_access_token;
	}

	public void setLogin_access_token(String login_access_token) {
		this.login_access_token = login_access_token;
	}
}
