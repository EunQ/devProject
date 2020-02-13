package com.ssafy.edu.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Apply;

@Repository
public interface ApplyRepo extends JpaRepository<Apply, Integer> {
	Optional<Apply> findByBoardIdAndTeamId(int boardId, int teamId);
}
