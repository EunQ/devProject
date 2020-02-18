package com.ssafy.edu.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer>{

	Post findOneByEmailAndBoardId(String email, int board_id);

	Post findOneByBoardId(int boardId);
}
