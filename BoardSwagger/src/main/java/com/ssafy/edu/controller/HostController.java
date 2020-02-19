package com.ssafy.edu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.Board;
import com.ssafy.edu.dto.Team;
import com.ssafy.edu.jpa.BoardRepo;
import com.ssafy.edu.jpa.PostRepo;
import com.ssafy.edu.jpa.TeamMemberRepo;
import com.ssafy.edu.jpa.TeamRepo;
import com.ssafy.edu.response.CommonResponse;
import com.ssafy.edu.service.HostService;
import com.ssafy.edu.service.JwtTokenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Api(tags = {"공모전 스폰서가 담당하는 부분, 팀 상태변화, 공모전 시작같은 부분을 맡음."})
@RestController
@RequestMapping(value="/api/sponsor")
@CrossOrigin("*")
public class HostController {

	/* !! 주최자 관련 부분. */
	private Logger logger = LoggerFactory.getLogger(HostController.class); 
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private TeamRepo teamRepo;
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private HostService hostService;
	
	@Autowired
	private BoardRepo boardRepo;
	
	@Autowired
	private TeamMemberRepo teamMemberRepo;
	

	@ApiOperation(value="주최한 공모전을 전부 표시", notes="공모전 전부 표시")
	@PatchMapping(value = "boards/{email}")
	public ResponseEntity<List<Board>> findAllBoardByHost(@ApiParam(value = "기존의  BoardId", required = true) @PathVariable String email){
		return new ResponseEntity<List<Board>>(boardRepo.findAllByHostEmail(email), HttpStatus.OK);
	}
	
	@ApiOperation(value="주최자가 공모전을 시작한다.", notes="팀의 상태가 아직도 Ready인 팀은 전부 삭제한다.")
	@PatchMapping(value = "/run/{boardId}")
	public ResponseEntity<CommonResponse> updateTeamStateToRun(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "기존의  BoardId", required = true) @PathVariable int boardId){
		//해당 boardId에 지원한 팀들 전부 컨펌으로.. 인원 제한을 넣는다.
		logger.info("----updateTeamStateToRun-----");
		String hostEmail = jwtTokenService.getUserPk(accessToken);
		if(!jwtTokenService.validateToken(accessToken)) {
			return CommonResponse.makeResponseEntity(-1, "token이 유효하지 않습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}
		if(!hostService.validateHost(boardId, hostEmail)) {
			return CommonResponse.makeResponseEntity(-1, "올바른 sponsor email이 아닙니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}
		//백엔드 토큰이 주최자의 것인지 확인하는용도.
		
		//이제까지 확정을 하지 않는 팀은 그냥 삭제
		teamRepo.deleteAllByBoardIdAndReady(boardId);
		teamRepo.flush();
		logger.info("-----------delete after -------");
		//teamMemberRepo.count
		int peopleNow = (int) teamMemberRepo.countByBoardId(boardId);
		Board board =  boardRepo.findById(boardId).orElse(null);
		board.setPeopleNow(peopleNow);
		boardRepo.save(board);
		boardRepo.flush();
		logger.info("board peoplenoe update ");
		logger.info(board.toString());
		
		return CommonResponse.makeResponseEntity(0, "팀들의 상태가 RUN인것들만 남김.", CommonResponse.SUCC, HttpStatus.OK);
	}
	
	@ApiOperation(value="주최자가 팀의 상태를 RUN으로 되돌림.", notes="해당 teamId를 RUN로 바꿈.")
	@PatchMapping(value = "/{boardId}/run/{teamId}")
	public ResponseEntity<CommonResponse> updateTeamStateTo(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "기존의  BoardId", required = true) @PathVariable int boardId, @ApiParam(value = "기존의  TeamId", required = true) @PathVariable int teamId){
		//해당 boardId에 지원한 팀들 전부 컨펌으로.. 인원 제한을 넣는다.
		logger.info("----updateTeamStateToHalf-----");
		String hostEmail = jwtTokenService.getUserPk(accessToken);
		if(!jwtTokenService.validateToken(accessToken)) {
			return CommonResponse.makeResponseEntity(-1, "token이 유효하지 않습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}
		if(!hostService.validateTeamIdWithHost(teamId, boardId, hostEmail)) {
			return CommonResponse.makeResponseEntity(-1, "올바른 host, teamId가 아닙니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}
		//백엔드 토큰이 주최자의 것인지 확인하는용도.
		
		Team team = teamRepo.findByTeamId(teamId).orElse(null);
		team.setTeamState(Team.STATE_RUN);
		teamRepo.save(team);
		teamRepo.flush();
		return CommonResponse.makeResponseEntity(0, "해당 팀의 상태를 run으로 변경", CommonResponse.SUCC, HttpStatus.OK);
	}
	
	@ApiOperation(value="주최자가 팀의 상태를 1차로 탈락시킬 팀", notes="해당 boardId를 END_HALF로 바꿔서 1차로 떨어진 팀으로 생각.")
	@PatchMapping(value = "/{boardId}/half/{teamId}")
	public ResponseEntity<CommonResponse> updateTeamStateToHalf(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "기존의  BoardId", required = true) @PathVariable int boardId, @ApiParam(value = "기존의  TeamId", required = true) @PathVariable int teamId){
		//해당 boardId에 지원한 팀들 전부 컨펌으로.. 인원 제한을 넣는다.
		logger.info("----updateTeamStateToHalf-----");
		String hostEmail = jwtTokenService.getUserPk(accessToken);
		if(!jwtTokenService.validateToken(accessToken)) {
			return CommonResponse.makeResponseEntity(-1, "token이 유효하지 않습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}
		if(!hostService.validateTeamIdWithHost(teamId, boardId, hostEmail)) {
			return CommonResponse.makeResponseEntity(-1, "올바른 host, teamId가 아닙니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}
		//백엔드 토큰이 주최자의 것인지 확인하는용도.
		
		Team team = teamRepo.findByTeamId(teamId).orElse(null);
		team.setTeamState(Team.STATE_END_HALF);
		teamRepo.save(team);
		teamRepo.flush();
		
		return CommonResponse.makeResponseEntity(0, "해당 팀의 상태를 run->end_half로 변경", CommonResponse.SUCC, HttpStatus.OK);
	}
	
	
	@ApiOperation(value="주최자가 팀의 상태를 최종 종료시키는", notes="해당 boardId를 END_FULL로 바꿔서 최종종료")
	@PatchMapping(value = "/{boardId}/full/{teamId}")
	public ResponseEntity<CommonResponse> updateTeamStateToFull(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "기존의  BoardId", required = true) @PathVariable int boardId, @ApiParam(value = "기존의  TeamId", required = true) @PathVariable int teamId){
		//해당 boardId에 지원한 팀들 전부 컨펌으로.. 인원 제한을 넣는다.
		logger.info("----updateTeamStateToHalf-----");
		String hostEmail = jwtTokenService.getUserPk(accessToken);
		if(!jwtTokenService.validateToken(accessToken)) {
			return CommonResponse.makeResponseEntity(-1, "token이 유효하지 않습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}
		if(!hostService.validateTeamIdWithHost(teamId, boardId, hostEmail)) {
			return CommonResponse.makeResponseEntity(-1, "올바른 host, teamId가 아닙니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}
		//백엔드 토큰이 주최자의 것인지 확인하는용도.
		
		Team team = teamRepo.findByTeamId(teamId).orElse(null);
		team.setTeamState(Team.STATE_END_FULL);
		teamRepo.save(team);
		teamRepo.flush();
		return CommonResponse.makeResponseEntity(0, "해당 팀의 상태를 run->end_half로 변경", CommonResponse.SUCC, HttpStatus.OK);
	}
	
}
