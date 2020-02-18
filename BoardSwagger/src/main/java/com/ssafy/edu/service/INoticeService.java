package com.ssafy.edu.service;

import java.util.List;

import com.ssafy.edu.dto.Notice;

public interface INoticeService {

	List<Notice> getNotice();

	Notice getNoticeByID(int notice_id);

	void addNotice(Notice notice);

	void deleteNotice(int notice_id);
	
}
