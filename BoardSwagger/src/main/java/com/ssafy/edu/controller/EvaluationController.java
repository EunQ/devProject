package com.ssafy.edu.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.TeamEvaluation;
import com.ssafy.edu.jpa.TeamEvaluationRepo;
import com.ssafy.edu.request.EvaluateTeamRequest;
import com.ssafy.edu.response.CommonResponse;
import com.ssafy.edu.service.JwtTokenService;
import com.ssafy.edu.service.HostService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"평가에 관련된 Controller 공모전 통과, 팀 평가, 팀원들간 평가 기능을 담당"})
@RestController
@RequestMapping(value="/api/eval")
@CrossOrigin("*")
public class EvaluationController {
	
	@Autowired
	private HostService hostService;

	final private Logger logger = LoggerFactory.getLogger(EvaluationController.class);
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private TeamEvaluationRepo teamEvaluationRepo;
	
	@ApiOperation(value="주최가가 해당 팀을 평가한다.", notes="리턴 값으로 succ, fail을 출력한다.")
	@PostMapping(value = "/host")
	public ResponseEntity<CommonResponse> evaluateTeamBySponsor(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			 @ApiParam(value = "평가하고자 하는 team_id, board_id, 내용 info", required = true) @RequestBody EvaluateTeamRequest evaluateTeamRequest){
		logger.info("---- evaluateTeamBySponsor ----");
		logger.info(evaluateTeamRequest.toString());
		int teamId = evaluateTeamRequest.getTeam_id();
		int boardId = evaluateTeamRequest.getBoard_id();
		String sponserEmail = jwtTokenService.getUserPk(accessToken);
		if(!jwtTokenService.validateToken(accessToken)) {
			return CommonResponse.makeResponseEntity(-1, "token이 유효하지 않습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}
		if(!hostService.validateTeamIdWithSponsor(teamId, boardId, sponserEmail)) {
			return CommonResponse.makeResponseEntity(-1, "TeamId, BoardId가 유효하지 않습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}	
		//여기까지 유효한지 아닌지 검증
		
		TeamEvaluation te = new TeamEvaluation();
		SimpleDateFormat dateFomat = new SimpleDateFormat("yyyy-MM-dd");
		te.setEDate(dateFomat.format(new Date()));
		te.setInfo(evaluateTeamRequest.getInfo());
		te.setScore(evaluateTeamRequest.getScore());
		te.setTeamId(evaluateTeamRequest.getTeam_id());
		teamEvaluationRepo.save(te);
		teamEvaluationRepo.flush();
		return CommonResponse.makeResponseEntity(0, "해당 팀 평가 저장.", CommonResponse.SUCC, HttpStatus.OK);
	}	
	
	//해당 팀에 대한 평가 표시
	
	//해당 보드에 대한 평가들 전부 표시
	
	@ApiOperation(value="주최가가 해당 팀평가를 수정.", notes="리턴 값으로 succ, fail을 출력한다.")
	@PatchMapping(value = "/sponsor")
	public ResponseEntity<CommonResponse> evaluateTeamUpdateBySponsor(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "평가하고자 하는 team_id, board_id", required = true) @RequestBody EvaluateTeamRequest evaluateTeamRequest){
		
		return null;
	}
	
}
