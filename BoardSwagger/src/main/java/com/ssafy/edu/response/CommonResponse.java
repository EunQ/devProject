package com.ssafy.edu.response;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommonResponse implements Serializable {
	protected int code;
	protected String msg;
	protected String state;
	final static public String SUCC = "succ";
	final static public String FAIL = "fail";
	
	static public ResponseEntity<CommonResponse> makeResponseEntity(int code, String msg, String state, HttpStatus hStatus){
		return new ResponseEntity<>(new CommonResponse(code, msg, state), hStatus);	
	}
	
}
