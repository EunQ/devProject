package com.ssafy.manv.dao;

import com.ssafy.manv.entity.GithubMember;
import com.ssafy.manv.entity.MemberAndGithub;

public interface GithubMemberMapper {
	public GithubMember findGithubMemberById(long id);
	public void save(GithubMember member);
	public void saveConnect(MemberAndGithub mag);
}
