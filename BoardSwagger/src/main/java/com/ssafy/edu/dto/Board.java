package com.ssafy.edu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {
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
	private int people_now;
	private String hashtag;
	
	public Board() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Board(int board_id, String title, String host, String apply_start, String apply_end, String start,
			String end, int people_num, int price, String location, String info, String img, int people_now,
			String hashtag) {
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
		this.people_now = people_now;
		this.hashtag = hashtag;
	}

	public Board(String title, String host, String apply_start, String apply_end, String start, String end,
			int people_num, int price, String location, String info, String img, int people_now) {
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
		this.people_now = people_now;
	}

	public Board(String title, String host, String apply_start, String apply_end, String start, String end,
			int people_num, int price, String location, String info, String img, int people_now, String hashtag) {
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
		this.people_now = people_now;
		this.hashtag = hashtag;
	}

	public Board(String title, String host, String apply_start, String apply_end, String start, String end,
			int people_num, int price, String location, String info, String hashtag) {
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
		this.hashtag = hashtag;
	}

	public Board(String title, String host, String apply_start, String apply_end, String start, String end,
			int people_num, int price, String location, String info) {
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
	}
	
//	public Board(int board_id, String title, String host, String apply_start, String apply_end, String start,
//			String end, int people_num, int price, String location, String info, String img, int people_now) {
//		super();
//		this.board_id = board_id;
//		this.title = title;
//		this.host = host;
//		this.apply_start = apply_start;
//		this.apply_end = apply_end;
//		this.start = start;
//		this.end = end;
//		this.people_num = people_num;
//		this.price = price;
//		this.location = location;
//		this.info = info;
//		this.img = img;
//		this.people_now = people_now;
//	}
//
//	public Board(String title, String host, String apply_start, String apply_end, String start, String end,
//			int people_num, int price, String location, String info, String img, int people_now) {
//		super();
//		this.title = title;
//		this.host = host;
//		this.apply_start = apply_start;
//		this.apply_end = apply_end;
//		this.start = start;
//		this.end = end;
//		this.people_num = people_num;
//		this.price = price;
//		this.location = location;
//		this.info = info;
//		this.img = img;
//		this.people_now = people_now;
//	}
//
//	public Board(String title, String host, String apply_start, String apply_end, String start, String end,
//			int people_num, int price, String location, String info, int people_now) {
//		super();
//		this.title = title;
//		this.host = host;
//		this.apply_start = apply_start;
//		this.apply_end = apply_end;
//		this.start = start;
//		this.end = end;
//		this.people_num = people_num;
//		this.price = price;
//		this.location = location;
//		this.info = info;
//		this.people_now = people_now;
//	}

}
