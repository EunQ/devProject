package com.ssafy.manv.entity;

import java.io.Serializable;

public class MemberAndGithub implements Serializable {
	private static final long serialVersionUID = 1L;
	private String member_id;
	private long github_id;
	public MemberAndGithub() {
		super();
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public long getGithub_id() {
		return github_id;
	}
	public void setGithub_id(long github_id) {
		this.github_id = github_id;
	}
	
	
}
