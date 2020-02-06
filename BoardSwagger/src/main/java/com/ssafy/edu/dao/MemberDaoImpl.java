package com.ssafy.edu.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Member;

@Repository
public class MemberDaoImpl {

String ns="ssafy.member.";
    
	@Autowired
	private SqlSession sqlSession;
	
	public Member getMemberByID(String email) { 
		return sqlSession.selectOne(ns + "getMemberByID", email);
	}

	public void addMember(Member dto) {
		sqlSession.insert(ns + "addMember", dto);
	}

	public void deleteMember(String email) {
		sqlSession.delete(ns + "deleteMember", email);
	}

	public void changeMemberInfo(Member dto) {
		sqlSession.update(ns + "changeMemberInfo", dto);
	}

	public void changePwd(Member dto) {
		sqlSession.update(ns + "changePwd", dto);
	}

}
