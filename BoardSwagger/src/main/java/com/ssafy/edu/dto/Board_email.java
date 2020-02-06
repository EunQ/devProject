package com.ssafy.edu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board_email {
	private int board_id;
	private String title;
	private String host;
	private String apply_start;
	private String apply_end;
	private String start;
	private String end;
	private int people_num;
	private int price;
	private String location;
	private String info;
	private String img;
	private String email;
	private int people_now;
	private String hashtag;
	
	public Board_email() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Board_email(int board_id, String title, String host, String apply_start, String apply_end, String start,
			String end, int people_num, int price, String location, String info, String img, String email,
			int people_now, String hashtag) {
		super();
		this.board_id = board_id;
		this.title = title;
		this.host = host;
		this.apply_start = apply_start;
		this.apply_end = apply_end;
		this.start = start;
		this.end = end;
		this.people_num = people_num;
		this.price = price;
		this.location = location;
		this.info = info;
		this.img = img;
		this.email = email;
		this.people_now = people_now;
		this.hashtag = hashtag;
	}

	public Board_email(String title, String host, String apply_start, String apply_end, String start, String end,
			int people_num, int price, String location, String info, String img, String email, int people_now) {
		super();
		this.title = title;
		this.host = host;
		this.apply_start = apply_start;
		this.apply_end = apply_end;
		this.start = start;
		this.end = end;
		this.people_num = people_num;
		this.price = price;
		this.location = location;
		this.info = info;
		this.img = img;
		this.email = email;
		this.people_now = people_now;
	}

	public Board_email(String title, String host, String apply_start, String apply_end, String start, String end,
			int people_num, int price, String location, String info, String img, String email, String hashtag) {
		super();
		this.title = title;
		this.host = host;
		this.apply_start = apply_start;
		this.apply_end = apply_end;
		this.start = start;
		this.end = end;
		this.people_num = people_num;
		this.price = price;
		this.location = location;
		this.info = info;
		this.img = img;
		this.email = email;
		this.hashtag = hashtag;
	}

}
