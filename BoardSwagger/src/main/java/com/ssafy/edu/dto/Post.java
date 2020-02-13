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
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private int postId;
	@Column(name = "post_date")
	private String postDate;
	private String email;
	@Column(name = "board_id")
	private int boardId;
	
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Post(int postId, String postDate, String email) {
		super();
		this.postId = postId;
		this.postDate = postDate;
		this.email = email;
	}
	
	public Post(String postDate, String email) {
		super();
		this.postDate = postDate;
		this.email = email;
	}
}
