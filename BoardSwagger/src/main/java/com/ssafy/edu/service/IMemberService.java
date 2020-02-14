package com.ssafy.edu.service;

import com.ssafy.edu.dto.Member;

public interface IMemberService {

	Member getMemberByID(String email);

	void addMember(Member dto);

	void deleteMember(String email);
	
	void changeMemberInfo(Member dto);
	
	void changePwd(Member dto);
}
