package com.ssafy.edu.dto;

public class Post {
	private int post_id;
	private String post_date;
	private String email;
	
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Post(int post_id, String post_date, String email) {
		super();
		this.post_id = post_id;
		this.post_date = post_date;
		this.email = email;
	}
	
	public Post(String post_date, String email) {
		super();
		this.post_date = post_date;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Post [post_id=" + post_id + ", post_date=" + post_date + ", email=" + email + "]";
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public String getPost_date() {
		return post_date;
	}

	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
