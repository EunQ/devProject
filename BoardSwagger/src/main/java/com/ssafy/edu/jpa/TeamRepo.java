package com.ssafy.edu.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Team;

@Repository
public interface TeamRepo extends JpaRepository<Team, Integer>{
	Optional<Team> findByTeamId(int team_id);
	List<Team> findAllByBoardId(int board_id);
	
}
