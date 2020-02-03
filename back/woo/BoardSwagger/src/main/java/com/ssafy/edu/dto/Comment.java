package com.ssafy.edu.dto;

public class Comment {
	private int cnum;
	private int board_id;
	private String email;
	private String ccontent;

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(int cnum, int board_id, String email, String ccontent) {
		super();
		this.cnum = cnum;
		this.board_id = board_id;
		this.email = email;
		this.ccontent = ccontent;
	}

	public Comment(int board_id, String email, String ccontent) {
		super();
		this.board_id = board_id;
		this.email = email;
		this.ccontent = ccontent;
	}

	@Override
	public String toString() {
		return "Comment [cnum=" + cnum + ", board_id=" + board_id + ", email=" + email + ", ccontent=" + ccontent
				+ "]";
	}

	public int getCnum() {
		return cnum;
	}

	public void setCnum(int cnum) {
		this.cnum = cnum;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCcontent() {
		return ccontent;
	}

	public void setCcontent(String ccontent) {
		this.ccontent = ccontent;
	}
}
