package com.ssafy.manv.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

public class CommonResponse implements Serializable {
	private int code;
	private String msg;
	public CommonResponse(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
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
	
}
