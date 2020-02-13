package com.ssafy.edu.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Team;
import com.ssafy.edu.dto.TeamMember;

@Repository
public interface TeamMemberRepo extends JpaRepository<TeamMember, Integer> {
	
	List<TeamMember> findAllByEmail(String email);
	
	Optional<TeamMember> findByEmailAndTeam(String email, Team team);
	
	//accpet가 0(수락중)이고 email에 대항하는 teamMember list를 리턴.. 응답을 해야할 팀원요청을 볼 수 있다.
	List<TeamMember> findAllByEmailAndAccept(String email, int accept);
	
	//accpet가 1(수락함)이고 teamId에 대항하는 teamMember list리턴
	List<TeamMember> findAllByTeamAndAccept(Team team, int accept); 
	
	
}
