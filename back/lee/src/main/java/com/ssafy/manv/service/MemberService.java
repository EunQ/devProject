package com.ssafy.manv.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.manv.dao.MemberMapper;
import com.ssafy.manv.entity.Member;

@Service
public class MemberService {
	
	@Autowired
	MemberMapper memberMapper;
	
	public List<Member> getMemList(){
		return memberMapper.getMemList();
	}
	
	public Member login(String email, String pwd) {
		Member mem  = null;
		mem  = memberMapper.login(email, pwd);
		return mem;
	}
	
	public Member findByMemberId(String id) {
		return memberMapper.findByMemberId(id);
	}
	
	public void save(Member member) {
		memberMapper.save(member);
	}
}
