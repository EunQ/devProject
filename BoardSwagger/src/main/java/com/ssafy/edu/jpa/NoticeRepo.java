package com.ssafy.edu.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.edu.dto.Notice;

public interface NoticeRepo extends JpaRepository<Notice, Integer>{

	Notice findOneByNoticeId(int noticeId);

}
