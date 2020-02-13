package com.ssafy.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dto.Apply;
import com.ssafy.edu.dto.Post;
import com.ssafy.edu.jpa.ApplyRepo;
import com.ssafy.edu.jpa.PostRepo;

@Service
public class HostService {
	

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ApplyRepo applyRepo;
	
	public boolean validateTeamIdWithSponsor(int teamId, int boardId, String sponserEmail) {
		// sponser id, board_id에 해당하는 Post를 찾는다.
		// team_id, board_id에 해당하는 apply를 찾는다.
		// 둘다 있으면 true;
		Post post = postRepo.findOneByEmailAndBoardId(sponserEmail, boardId);
		Apply apply = applyRepo.findByBoardIdAndTeamId(boardId, teamId).orElse(null);
		if(post == null || apply == null) {
			return false;
		}
		return true;
	}
	
	public boolean validateSponsor(int boardId, String sponserEmail) {
		// sponser id, board_id에 해당하는 Post를 찾는다.
		// 없으면 null
		Post post = postRepo.findOneByEmailAndBoardId(sponserEmail, boardId);
		if(post == null) {
			return false;
		}
		return true;
	}
}
