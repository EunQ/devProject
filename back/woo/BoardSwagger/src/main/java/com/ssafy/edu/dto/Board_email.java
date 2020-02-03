package com.ssafy.edu.dto;

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
	
	public Board_email() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Board_email(int board_id, String title, String host, String apply_start, String apply_end, String start,
			String end, int people_num, int price, String location, String info, String img, String email, int people_now) {
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
			int people_num, int price, String location, String info, String img, String email) {
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
	}	

	public Board_email(String title, String host, String apply_start, String apply_end, String start, String end,
			int people_num, int price, String location, String info, String email) {
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
		this.email = email;
	}

	@Override
	public String toString() {
		return "Board_email [board_id=" + board_id + ", title=" + title + ", host=" + host + ", apply_start="
				+ apply_start + ", apply_end=" + apply_end + ", start=" + start + ", end=" + end + ", people_num="
				+ people_num + ", price=" + price + ", location=" + location + ", info=" + info + ", img=" + img
				+ ", email=" + email + ", people_now=" + people_now + "]";
	}

	public int getBoard_id() {
		return board_id;
	}

	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getApply_start() {
		return apply_start;
	}

	public void setApply_start(String apply_start) {
		this.apply_start = apply_start;
	}

	public String getApply_end() {
		return apply_end;
	}

	public void setApply_end(String apply_end) {
		this.apply_end = apply_end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getPeople_num() {
		return people_num;
	}

	public void setPeople_num(int people_num) {
		this.people_num = people_num;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPeople_now() {
		return people_now;
	}

	public void setPeople_now(int people_now) {
		this.people_now = people_now;
	}
}
