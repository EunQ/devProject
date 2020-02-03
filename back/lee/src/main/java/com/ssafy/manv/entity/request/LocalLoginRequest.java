package com.ssafy.manv.entity.request;

import java.io.Serializable;

public class LocalLoginRequest implements Serializable {
	String id;
	String pwd;
	
	public LocalLoginRequest() {
		super();
	}

	public LocalLoginRequest(String id, String pwd) {
		super();
		this.id = id;
		this.pwd = pwd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	

	
	
}
