package com.ssafy.edu.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cnum;
	@Column(name = "board_id")
	private int boardId;
	private String email;
	private String ccontent;

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(int cnum, int boardId, String email, String ccontent) {
		super();
		this.cnum = cnum;
		this.boardId = boardId;
		this.email = email;
		this.ccontent = ccontent;
	}

	public Comment(int boardId, String email, String ccontent) {
		super();
		this.boardId = boardId;
		this.email = email;
		this.ccontent = ccontent;
	}
}
