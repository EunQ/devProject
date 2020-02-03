package com.ssafy.edu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Member implements Serializable, UserDetails{
	private static final long serialVersionUID = 1L;
	private String email;
	private String pwd;
	private String phone;
	private String name;
	private int auth;
	private String job;
	private String token;
	private String info;
	private int birth;
	
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Member(String email, String pwd, String phone, String name, int auth, String job, String token, String info,
			int birth) {
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
	}

	public Member(String email, String pwd, String phone, String name, String job, int birth) {
		super();
		this.email = email;
		this.pwd = pwd;
		this.phone = phone;
		this.name = name;
		this.job = job;
		this.birth = birth;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.auth == 0) {
			ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
	        auth.add(new SimpleGrantedAuthority("USER"));
	        return auth;
		}
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority("ADMIN"));
        return auth;

	}

	@Override
	public String getPassword() {
		return this.pwd;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "Member [email=" + email + ", pwd=" + pwd + ", phone=" + phone + ", name=" + name + ", auth=" + auth
				+ ", job=" + job + ", token=" + token + ", info=" + info + ", birth=" + birth + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getBirth() {
		return birth;
	}

	public void setBirth(int birth) {
		this.birth = birth;
	}
	
	public String getRole() {
		if(this.auth == 0) {
			return "USER";
		}
		return "ADMIN";
	}
	
}
