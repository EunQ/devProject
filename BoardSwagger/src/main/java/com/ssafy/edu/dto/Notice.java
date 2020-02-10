package com.ssafy.edu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Notice {
	private int notice_id;
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
