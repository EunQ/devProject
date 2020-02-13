package com.ssafy.edu.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.TeamEvaluation;

@Repository
public interface TeamEvaluationRepo extends JpaRepository<TeamEvaluation, Integer>{
	
}
