package com.ssafy.edu.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;

public class SingleResult<T> extends CommonResponse {

	private T data;
	public SingleResult(int code, String msg, String state) {
		super(code, msg, state);
		this.data = null;
	}
	
	public SingleResult(int code, String msg, String state, T data) {
		super(code, msg, state);
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "SingleResult [data=" + data + "]";
	}
	
	public static <T> ResponseEntity<SingleResult<T>> makeResponseEntity(int code, String msg, String state, T data, HttpStatus hStatus){
		return new ResponseEntity<SingleResult<T>>(new SingleResult(code, msg, state, data), hStatus);	
	}
}
