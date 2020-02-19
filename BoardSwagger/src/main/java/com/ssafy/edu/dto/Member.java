package com.ssafy.edu.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.Persistent;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name = "cmember")
@Getter
@Setter
public class Member implements Serializable{
	
	@Id
	private String email;
	
	private String pwd;
	private String phone;
	private String name;
	
	@ColumnDefault("USER")
	private String auth;
	
	private String job;
	private String token;
	private String info;
	private String birth;
	private String github;

	final static public String USER = "USER";
	final static public String ADMIN = "ADMIN"; 
	
	public Member() {
		super();
	}

	public Member(String email, String pwd, String phone, String name, String auth, String job, String token,
			String info, String birth, String github) {
		super();
		this.email = email;
		this.pwd = pwd;
		this.phone = phone;
		this.name = name;
		this.auth = auth;
		this.job = job;
		this.token = token;
		this.info = info;
		this.birth = birth;
		this.github = github;
	}

	@Override
	public String toString() {
		return "Member [email=" + email + ", pwd=" + pwd + ", phone=" + phone + ", name=" + name + ", auth=" + auth
				+ ", job=" + job + ", token=" + token + ", info=" + info + ", birth=" + birth + "]";
	}
	
	@PrePersist
    public void prePersist() {
        this.auth = this.auth == null ? "USER" : this.auth;
    }
}
