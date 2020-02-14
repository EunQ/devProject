package com.ssafy.edu.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Apply;
import com.ssafy.edu.dto.TeamEvaluation;

@Repository
public interface TeamEvaluationRepo extends JpaRepository<TeamEvaluation, Integer>{
	
}
