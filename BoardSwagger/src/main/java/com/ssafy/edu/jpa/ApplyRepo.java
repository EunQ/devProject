package com.ssafy.edu.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Apply;
import com.ssafy.edu.dto.Team;

@Repository
public interface ApplyRepo extends JpaRepository<Apply, Integer> {
	Optional<Apply> findByBoardIdAndTeam(int boardId, Team team);
	List<Apply> findAllByBoardId(int boardId);
}
