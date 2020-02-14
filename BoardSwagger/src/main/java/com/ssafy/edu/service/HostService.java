package com.ssafy.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dto.Apply;
import com.ssafy.edu.dto.Post;
import com.ssafy.edu.dto.Team;
import com.ssafy.edu.jpa.ApplyRepo;
import com.ssafy.edu.jpa.PostRepo;
import com.ssafy.edu.jpa.TeamRepo;

@Service
public class HostService {
	

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ApplyRepo applyRepo;
	
	@Autowired
	private TeamRepo teamRepo;
	
	public boolean validateTeamIdWithHost(int teamId, int boardId, String hostEmail) {
		// 해당 팀을 평가할 자격이 있는지.
		// sponser id, board_id에 해당하는 Post를 찾는다.
		// team_id, board_id에 해당하는 apply를 찾는다.
		// 둘다 있으면 true;
		Post post = postRepo.findOneByEmailAndBoardId(hostEmail, boardId);
		Team team = teamRepo.findByTeamId(teamId).orElse(null);
		if(team == null || post == null) {
			return false;
		}
		Apply apply = applyRepo.findByBoardIdAndTeam(boardId, team).orElse(null);
		if(apply == null) {
			return false;
		}
		return true;
	}
	
	public boolean validateHost(int boardId, String hostEmail) {
		// 공모전을 올린사람이 hostEmail인지 확인.
		// sponser id, board_id에 해당하는 Post를 찾는다.
		// 없으면 null
		Post post = postRepo.findOneByEmailAndBoardId(hostEmail, boardId);
		if(post == null) {
			return false;
		}
		return true;
	}
}
