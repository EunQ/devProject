package com.ssafy.edu.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.Team;
import com.ssafy.edu.dto.TeamEvaluation;
import com.ssafy.edu.jpa.TeamEvaluationRepo;
import com.ssafy.edu.jpa.TeamRepo;
import com.ssafy.edu.request.EvaluateTeamRequest;
import com.ssafy.edu.request.UpdateEvaluateTeamRequest;
import com.ssafy.edu.request.UpdateTeamRequest;
import com.ssafy.edu.response.CommonResponse;
import com.ssafy.edu.response.SingleResult;
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
	
	@Autowired
	private TeamRepo teamRepo;
	
	@ApiOperation(value="주최가가 해당 팀을 평가한다.", notes="리턴 값으로 succ, fail을 출력한다.")
	@PostMapping(value = "/host")
	public ResponseEntity<CommonResponse> evaluateTeam(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			 @ApiParam(value = "평가하고자 하는 team_id, board_id, 내용 info", required = true) @RequestBody EvaluateTeamRequest evaluateTeamRequest){
		logger.info("------------------- evaluateTeamByHost --------------------");
		logger.info(evaluateTeamRequest.toString());
		int teamId = evaluateTeamRequest.getTeam_id();
		int boardId = evaluateTeamRequest.getBoard_id();
		String hostEmail = jwtTokenService.getUserPk(accessToken);
		if(!jwtTokenService.validateToken(accessToken)) {
			return CommonResponse.makeResponseEntity(-1, "token이 유효하지 않습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}
		if(!hostService.validateTeamIdWithHost(teamId, boardId, hostEmail)) {
			return CommonResponse.makeResponseEntity(-1, "TeamId, BoardId가 유효하지 않습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}	
		//여기까지 유효한지 아닌지 검증
		
		TeamEvaluation te = new TeamEvaluation();
		SimpleDateFormat dateFomat = new SimpleDateFormat("yyyy-MM-dd");
		te.setEDate(dateFomat.format(new Date()));
		te.setInfo(evaluateTeamRequest.getInfo());
		te.setScore(evaluateTeamRequest.getScore());
		te.setTeamId(evaluateTeamRequest.getTeam_id());
		te.setHost(hostEmail);
		teamEvaluationRepo.save(te);
		teamEvaluationRepo.flush();
		return CommonResponse.makeResponseEntity(0, "해당 팀 평가 저장.", CommonResponse.SUCC, HttpStatus.OK);
	}	
	
	//해당 팀에 대한 평가 표시
	@ApiOperation(value="해당 공모전에 지원한 한 팀에 대한 평가를 보기", notes="리턴 값으로 succ, fail을 출력한다.")
	@GetMapping(value = "/host/{boardId}/spec/{teamId}")
	public ResponseEntity<SingleResult<TeamEvaluation>> getTeamEvaluation(@ApiParam(value = "공모전 id", required = true) @PathVariable Integer boardId, 
			@ApiParam(value = "팀 id", required = true) @PathVariable Integer teamId){
		logger.info("-------------------- getTeamEvaluation -------------------");
		
		TeamEvaluation te = teamEvaluationRepo.findByBoardIdAndTeamId(boardId, teamId).orElse(null);
		if(te == null) {
			return new ResponseEntity<>(new SingleResult<>(-1, "해당 평가가 없음", CommonResponse.FAIL),HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new SingleResult<>(0, "평가를 보냈음.", CommonResponse.SUCC, te),HttpStatus.OK);
	}	
	//해당 보드에 대한 평가들 전부 표시
	@ApiOperation(value="해당 공모전에 지원한 전체팀에 대한 평가를 보기", notes="리턴 값으로 succ, fail을 출력한다.")
	@GetMapping(value = "/host/{boardId}")
	public ResponseEntity<SingleResult<List<TeamEvaluation>>> getTeamsEvaluation(@ApiParam(value = "공모전 id", required = true) @PathVariable Integer boardId){
		logger.info("-------------------- getTeamsEvaluation --------------------");
		List<TeamEvaluation> res = teamEvaluationRepo.findAllByBoardId(boardId);
		return new ResponseEntity<>(new SingleResult<>(0, "평가를 보냈음.", CommonResponse.SUCC, res),HttpStatus.OK);
	}	
	
	
	//host가 평가를 수정하고자 할때.
	@ApiOperation(value="주최가가 해당 팀평가를 수정.", notes="리턴 값으로 succ, fail을 출력한다.")
	@PatchMapping(value = "/host/{teamEvaluationId}")
	public ResponseEntity<CommonResponse> updateEvaluateTeam(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "수정하고자 하는 id", required = true) @PathVariable Integer teamEvaluationId,
			@ApiParam(value = "수정 내용", required = true) @RequestBody UpdateTeamRequest request){
		logger.info("------------------- updateEvaluateTeam -----------------");
		String hostEmail = jwtTokenService.getUserPk(accessToken);
		if(!jwtTokenService.validateToken(accessToken)) {
			return CommonResponse.makeResponseEntity(-1, "token이 유효하지 않습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}
		TeamEvaluation te = teamEvaluationRepo.findById(teamEvaluationId).orElse(null);
		if(te == null) {
			return CommonResponse.makeResponseEntity(-1, "teamEvaluationId가 유효하지 않습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}	
		if(!te.getHost().equals(hostEmail)) {
			return CommonResponse.makeResponseEntity(-1, "해당 host가 평가하지 않았습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
		}	
		//여기까지 유효한지 아닌지 검증
		SimpleDateFormat dateFomat = new SimpleDateFormat("yyyy-MM-dd");
		te.setEDate(dateFomat.format(new Date()));
		te.setInfo(request.getInfo());
		te.setScore(request.getScore());
		teamEvaluationRepo.save(te);
		teamEvaluationRepo.flush();
		return CommonResponse.makeResponseEntity(0, "해당 팀 평가 수정.", CommonResponse.SUCC, HttpStatus.OK);
	}	
	
//	
//	//팀원들이 팀원들을 평가하는 기능.
//	@ApiOperation(value="내가 받은 평가들  전체 보기", notes="리턴 값으로 succ, fail을 출력한다.")
//	@GetMapping(value = "/member/{fromId}/to/{toId}")
//	public ResponseEntity<SingleResult<TeamEvaluation>> getTeamEvaluation(@ApiParam(value = "공모전 id", required = true) @PathVariable Integer boardId, 
//			@ApiParam(value = "팀 id", required = true) @PathVariable Integer teamId){
//		logger.info("-------------------- getTeamEvaluation -------------------");
//		
//		TeamEvaluation te = teamEvaluationRepo.findByBoardIdAndTeamId(boardId, teamId).orElse(null);
//		if(te == null) {
//			return new ResponseEntity<>(new SingleResult<>(-1, "해당 평가가 없음", CommonResponse.FAIL),HttpStatus.BAD_REQUEST);
//		}
//
//		return new ResponseEntity<>(new SingleResult<>(0, "평가를 보냈음.", CommonResponse.SUCC, te),HttpStatus.OK);
//	}		
//	
//	@ApiOperation(value="같은 팀내에서 내가 받은 평가들  전체 보기", notes="리턴 값으로 succ, fail을 출력한다.")
//	@GetMapping(value = "/member/{fromId}/to/{toId}")
//	public ResponseEntity<SingleResult<TeamEvaluation>> getTeamEvaluation(@ApiParam(value = "공모전 id", required = true) @PathVariable Integer boardId, 
//			@ApiParam(value = "팀 id", required = true) @PathVariable Integer teamId){
//		logger.info("-------------------- getTeamEvaluation -------------------");
//		
//		TeamEvaluation te = teamEvaluationRepo.findByBoardIdAndTeamId(boardId, teamId).orElse(null);
//		if(te == null) {
//			return new ResponseEntity<>(new SingleResult<>(-1, "해당 평가가 없음", CommonResponse.FAIL),HttpStatus.BAD_REQUEST);
//		}
//
//		return new ResponseEntity<>(new SingleResult<>(0, "평가를 보냈음.", CommonResponse.SUCC, te),HttpStatus.OK);
//	}
//	
//	@ApiOperation(value="내가 한 평가들 보기 ", notes="리턴 값으로 succ, fail을 출력한다.")
//	@GetMapping(value = "/member/{fromId}/to/{toId}")
//	public ResponseEntity<SingleResult<TeamEvaluation>> getTeamEvaluation(@ApiParam(value = "공모전 id", required = true) @PathVariable Integer boardId, 
//			@ApiParam(value = "팀 id", required = true) @PathVariable Integer teamId){
//		logger.info("-------------------- getTeamEvaluation -------------------");
//		
//		TeamEvaluation te = teamEvaluationRepo.findByBoardIdAndTeamId(boardId, teamId).orElse(null);
//		if(te == null) {
//			return new ResponseEntity<>(new SingleResult<>(-1, "해당 평가가 없음", CommonResponse.FAIL),HttpStatus.BAD_REQUEST);
//		}
//
//		return new ResponseEntity<>(new SingleResult<>(0, "평가를 보냈음.", CommonResponse.SUCC, te),HttpStatus.OK);
//	}
//	
//	@ApiOperation(value="내가 같은팀 팀원들에게 한 평가들 보기 ", notes="리턴 값으로 succ, fail을 출력한다.")
//	@GetMapping(value = "/member/{fromId}/to/{toId}")
//	public ResponseEntity<SingleResult<TeamEvaluation>> getTeamEvaluation(@ApiParam(value = "공모전 id", required = true) @PathVariable Integer boardId, 
//			@ApiParam(value = "팀 id", required = true) @PathVariable Integer teamId){
//		logger.info("-------------------- getTeamEvaluation -------------------");
//		
//		TeamEvaluation te = teamEvaluationRepo.findByBoardIdAndTeamId(boardId, teamId).orElse(null);
//		if(te == null) {
//			return new ResponseEntity<>(new SingleResult<>(-1, "해당 평가가 없음", CommonResponse.FAIL),HttpStatus.BAD_REQUEST);
//		}
//
//		return new ResponseEntity<>(new SingleResult<>(0, "평가를 보냈음.", CommonResponse.SUCC, te),HttpStatus.OK);
//	}
}
