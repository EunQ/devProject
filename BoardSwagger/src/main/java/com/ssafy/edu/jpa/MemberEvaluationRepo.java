package com.ssafy.edu.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.edu.dto.MemberEvaluation;

public interface MemberEvaluationRepo extends JpaRepository<MemberEvaluation, Integer> {
	List<MemberEvaluation> findAllByFromMember(String fromMember);
	List<MemberEvaluation> findAllByToMember(String toMember);

}
