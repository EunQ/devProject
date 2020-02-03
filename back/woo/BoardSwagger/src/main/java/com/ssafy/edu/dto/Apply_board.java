package com.ssafy.edu.dto;

public class Apply_board {
	private int board_id;
	private int apply_id;
	private String email;
	
	public Apply_board() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Apply_board(int board_id, int apply_id, String email) {
		super();
		this.board_id = board_id;
		this.apply_id = apply_id;
		this.email = email;
	}
	
	public Apply_board(int board_id, String email) {
		super();
		this.board_id = board_id;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Apply_board [board_id=" + board_id + ", apply_id=" + apply_id + ", email=" + email + "]";
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public int getApply_id() {
		return apply_id;
	}

	public void setApply_id(int apply_id) {
		this.apply_id = apply_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
