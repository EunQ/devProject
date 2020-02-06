package com.ssafy.edu.response;

public class SingleResult<T> extends CommonResponse {

	private T data;
	public SingleResult(int code, String msg, String state) {
		super(code, msg, state);
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
	
}
