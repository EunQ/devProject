package com.ssafy.edu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dao.NoticeDaoImpl;
import com.ssafy.edu.dto.Notice;

@Service
public class NoticeServiceImple implements INoticeService {

	@Autowired
	private NoticeDaoImpl dao;
	
	@Override
	public List<Notice> getNotice() {
		return dao.getNotice();
	}

	@Override
	public Notice getNoticeByID(int notice_id) {
		return dao.getNoticeByID(notice_id);
	}

	@Override
	public void addNotice(Notice notice) {
		dao.addNotice(notice);
	}

	@Override
	public void deleteNotice(int notice_id) {
		dao.deleteNotice(notice_id);
	}

}
