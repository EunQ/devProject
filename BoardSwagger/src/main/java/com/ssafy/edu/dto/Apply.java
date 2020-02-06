package com.ssafy.edu.dto;

public class Apply {
	private int apply_id;
	private String apply_date;
	private String email;
	
	public Apply() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Apply(int apply_id, String apply_date, String email) {
		super();
		this.apply_id = apply_id;
		this.apply_date = apply_date;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Apply [apply_id=" + apply_id + ", apply_date=" + apply_date + ", email=" + email + "]";
	}

	public int getApply_id() {
		return apply_id;
	}

	public void setApply_id(int apply_id) {
		this.apply_id = apply_id;
	}

	public String getApply_date() {
		return apply_date;
	}

	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
