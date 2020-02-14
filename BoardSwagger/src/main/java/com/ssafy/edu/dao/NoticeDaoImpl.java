package com.ssafy.edu.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Notice;

@Repository
public class NoticeDaoImpl {
	
	String ns="ssafy.notice.";
	
	@Autowired
	private SqlSession sqlSession;

	public List<Notice> getNotice() {
		return sqlSession.selectList(ns + "getNotice");
	}

	public Notice getNoticeByID(int notice_id) {
		return sqlSession.selectOne(ns + "getNoticeByID", notice_id);
	}

	public void addNotice(Notice notice) {
		sqlSession.insert(ns + "addNotice", notice);
	}

	public void deleteNotice(int notice_id) {
		sqlSession.delete(ns + "deleteNotice", notice_id);
	}

}
