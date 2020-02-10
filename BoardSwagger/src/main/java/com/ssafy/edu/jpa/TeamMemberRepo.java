package com.ssafy.edu.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.TeamMember;

@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, Integer> {
	
	List<TeamMember> findAllByEmail(String email);
}
