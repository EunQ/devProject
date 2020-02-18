package com.ssafy.edu.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Notice;

@Repository
public interface NoticeRepo extends JpaRepository<Notice, Integer>{

	Notice findOneByNoticeId(int noticeId);

}
