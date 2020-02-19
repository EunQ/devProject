package com.ssafy.edu.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
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
	private int peopleNow;
	private String hashtag;
	private String postDate;
	
	public BoardResponse(int boardId, String title, String host, String applyStart, String applyEnd, String start,
			String end, int peopleNum, int price, String location, String info, String img, int peopleNow, String postDate) {
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
		this.postDate = postDate;
	}

}
