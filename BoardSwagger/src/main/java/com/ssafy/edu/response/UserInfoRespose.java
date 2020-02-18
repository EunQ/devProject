package com.ssafy.edu.response;

import com.ssafy.edu.dto.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserInfoRespose extends CommonResponse {
	private Member member;
	
	public UserInfoRespose(int code, String msg, String state, Member member) {
		this.member = member;
		this.code = code;
		this.msg = msg;
		this.state = state;
	}
}
