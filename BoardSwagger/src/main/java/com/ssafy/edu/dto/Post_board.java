package com.ssafy.edu.dto;

public class Post_board {
	private int post_id;
	private String email;
	private int board_id;
	
	public Post_board() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Post_board(int post_id, String email, int board_id) {
		super();
		this.post_id = post_id;
		this.email = email;
		this.board_id = board_id;
	}
	
	public Post_board(String email, int board_id) {
		super();
		this.email = email;
		this.board_id = board_id;
	}

	@Override
	public String toString() {
		return "Post_board [post_id=" + post_id + ", email=" + email + ", board_id=" + board_id + "]";
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
}
