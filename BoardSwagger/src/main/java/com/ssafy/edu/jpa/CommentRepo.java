package com.ssafy.edu.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer>{

	List<Comment> findAllByBoardId(int boardId);

}
