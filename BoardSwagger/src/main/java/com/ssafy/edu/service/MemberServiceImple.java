package com.ssafy.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dao.MemberDaoImpl;
import com.ssafy.edu.dto.Member;

@Service
public class MemberServiceImple implements IMemberService {

	@Autowired
	private MemberDaoImpl dao;

	@Override
	public Member getMemberByID(String email) {
		return dao.getMemberByID(email);
	}

	@Override
	public void addMember(Member dto) {
//		String encodedPassword = new BCryptPasswordEncoder().encode(dto.getPwd());
//		dto.setPwd(encodedPassword);
		dao.addMember(dto);
	}

	@Override
	public void deleteMember(String email) {
		dao.deleteMember(email);
	}

	@Override
	public void changeMemberInfo(Member dto) {
		dao.changeMemberInfo(dto);
	}

	@Override
	public void changePwd(Member dto) {
		dao.changePwd(dto);
	}
}
