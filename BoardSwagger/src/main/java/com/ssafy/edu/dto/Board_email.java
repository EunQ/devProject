package com.ssafy.edu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board_email {
	private int boardId;
	private String title;
	private String host;
	private String applyStart;
	private String applyEnd;
	private String start;
	private String end;
	private int peopleNum;
	private int price;
	private String location;
	private String info;
	private String img;
	private String email;
	private int peopleNow;
	private String hashtag;
	
	public Board_email() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Board_email(int boardId, String title, String host, String applyStart, String applyEnd, String start,
			String end, int peopleNum, int price, String location, String info, String img, String email,
			int peopleNow, String hashtag) {
		super();
		this.boardId = boardId;
		this.title = title;
		this.host = host;
		this.applyStart = applyStart;
		this.applyEnd = applyEnd;
		this.start = start;
		this.end = end;
		this.peopleNum = peopleNum;
		this.price = price;
		this.location = location;
		this.info = info;
		this.img = img;
		this.email = email;
		this.peopleNow = peopleNow;
		this.hashtag = hashtag;
	}

	public Board_email(String title, String host, String applyStart, String applyEnd, String start, String end,
			int peopleNum, int price, String location, String info, String img, String email, int peopleNow) {
		super();
		this.title = title;
		this.host = host;
		this.applyStart = applyStart;
		this.applyEnd = applyEnd;
		this.start = start;
		this.end = end;
		this.peopleNum = peopleNum;
		this.price = price;
		this.location = location;
		this.info = info;
		this.img = img;
		this.email = email;
		this.peopleNow = peopleNow;
	}

	public Board_email(String title, String host, String applyStart, String applyEnd, String start, String end,
			int peopleNum, int price, String location, String info, String img, String email, String hashtag) {
		super();
		this.title = title;
		this.host = host;
		this.applyStart = applyStart;
		this.applyEnd = applyEnd;
		this.start = start;
		this.end = end;
		this.peopleNum = peopleNum;
		this.price = price;
		this.location = location;
		this.info = info;
		this.img = img;
		this.email = email;
		this.hashtag = hashtag;
	}

}
