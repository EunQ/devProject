package com.ssafy.edu.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.MemberEvaluation;

@Repository
public interface MemberEvaluationRepo extends JpaRepository<MemberEvaluation, Integer> {
	List<MemberEvaluation> findAllByFromMember(String fromMember);
	List<MemberEvaluation> findAllByToMember(String toMember);
	List<MemberEvaluation> findAllByToMemberAndTeamId(String toMember, int teamId);
	List<MemberEvaluation> findAllByFromMemberAndTeamId(String fromMember, int teamId);
	
}
