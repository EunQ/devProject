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
@Table(name = "notice")
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id")
	private int noticeId;
	private String title;
	private String content;
	private int ncheck;
	private String date;
	
	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Notice(String title, String content, int ncheck, String date) {
		super();
		this.title = title;
		this.content = content;
		this.ncheck = ncheck;
		this.date = date;
	}
}
