package com.ssafy.edu.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Apply;
import com.ssafy.edu.dto.TeamEvaluation;

@Repository
public interface TeamEvaluationRepo extends JpaRepository<TeamEvaluation, Integer>{
	//팀은 하나의 공모전만 지원가능하니 팀 아이디만 있으면 해당평가를 볼 수 있음.
	Optional<TeamEvaluation> findByTeamId(int teamId);
	
	@Query(nativeQuery = true, value = "select * from team_evaluation where team_id in (select team_id from appy where board_id = :board_id)")
	List<TeamEvaluation> findAllByBoardId(@Param("board_id") int boardId);
	
}
