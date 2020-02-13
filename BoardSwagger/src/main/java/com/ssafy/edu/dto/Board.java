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
@Table(name = "board")
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private int boardId;
	private String title;
	private String host;
	@Column(name = "apply_start")
	private String applyStart;
	@Column(name = "apply_end")
	private String applyEnd;
	private String start;
	private String end;
	@Column(name = "people_num")
	private int peopleNum;
	private int price;
	private String location;
	private String info;
	private String img;
	@Column(name = "people_now")
	private int peopleNow;
	private String hashtag;
	
	public Board() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Board(int boardId, String title, String host, String applyStart, String applyEnd, String start,
			String end, int peopleNum, int price, String location, String info, String img, int peopleNow,
			String hashtag) {
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
		this.peopleNow = peopleNow;
		this.hashtag = hashtag;
	}

	public Board(String title, String host, String applyStart, String applyEnd, String start, String end,
			int peopleNum, int price, String location, String info, String img, int peopleNow) {
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
		this.peopleNow = peopleNow;
	}

	public Board(String title, String host, String applyStart, String applyEnd, String start, String end,
			int peopleNum, int price, String location, String info, String img, int peopleNow, String hashtag) {
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
		this.peopleNow = peopleNow;
		this.hashtag = hashtag;
	}

	public Board(String title, String host, String applyStart, String applyEnd, String start, String end,
			int peopleNum, int price, String location, String info, String hashtag) {
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
		this.hashtag = hashtag;
	}

	public Board(String title, String host, String applyStart, String applyEnd, String start, String end,
			int peopleNum, int price, String location, String info) {
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
	}
	
}
