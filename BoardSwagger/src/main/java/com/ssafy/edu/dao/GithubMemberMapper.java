package com.ssafy.edu.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.GithubMember;

@Repository
public class GithubMemberMapper {
	
	@Autowired
	private SqlSession sqlSession;
	
	String ns = "ssafy.githubMember.";
	
	public GithubMember findGithubMemberById(long id) {
		return sqlSession.selectOne(ns+"findGithubMemberById", id);
	}
	public void save(GithubMember member) {
		sqlSession.insert(ns+"save",member);
	}
}
