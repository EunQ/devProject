package com.ssafy.edu.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

public class CommonResponse implements Serializable {
	protected int code;
	protected String msg;
	protected String state;
	final static public String SUCC = "succ";
	final static public String FAIL = "fail";
	 
	public CommonResponse() {
		super();
	}
	public CommonResponse(int code, String msg, String state) {
		super();
		this.code = code;
		this.msg = msg;
		this.state = state;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
