package com.ssafy.edu.jpa;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Board;
import com.ssafy.edu.dto.Member;

@Repository
public interface BoardRepo extends JpaRepository<Board, Integer>{
	
	@Query(nativeQuery = true, value = "SELECT * FROM BOARD WHERE TITLE LIKE CONCAT('%', :keyword, '%')")
	List<Board> findByKeyword(@Param("keyword") String keyword);
	
	@Query(nativeQuery = true, value = "SELECT LAST_INSERT_ID()")
	int getBoardId();

	Optional<Board> findOneByBoardId(int boardId);
	
	@Query(nativeQuery = true, value = "select * from Board b where b.board_id in (select p.board_id from Post p where p.email = :email)")
	List<Board> findAllByHostEmail(@Param("email") String email);
	
	@Query(nativeQuery = true, value = "select * from board order by board_id desc limit 1")
	Board findRecentBoard();
}
