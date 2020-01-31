package com.ssafy.manv.dao;

import java.util.List;

import com.ssafy.manv.entity.Member;

public interface MemberMapper {
	public List<Member> getMemList();
	public Member login(String id, String pwd);
	public Member findByMemberId(String id);
	public void save(Member member);
}
