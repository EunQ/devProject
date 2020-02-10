package com.ssafy.edu.controller;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.Member;
import com.ssafy.edu.dto.Repository;
import com.ssafy.edu.dto.Team;
import com.ssafy.edu.jpa.MemberRepo;
import com.ssafy.edu.jpa.TeamRepo;
import com.ssafy.edu.request.AddTeamMemberRequest;
import com.ssafy.edu.request.ApplyAsLeaderRequest;
import com.ssafy.edu.dto.CodeRequest;
import com.ssafy.edu.response.CommonResponse;
import com.ssafy.edu.response.GithubAccessTokenRespose;
import com.ssafy.edu.response.SingleResult;
import com.ssafy.edu.service.JwtTokenService;
import com.ssafy.edu.service.RepositoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"Team관련 Controller Team에 관련된 정보와 팀장으로서 등록, 팀원으로서 등록부분을 담당"})
@RestController
@RequestMapping(value="/api/team")
@CrossOrigin("*")
public class TeamController {
	
	/*
	 * @ApiOperation(value="로그인", notes="이메일로 로그인을 하는 리턴값으로 토큰을 발행")
	@PostMapping(value = "/github/accessToken")
	public AccessTokenRequest getGithubAccessToken(	@ApiParam(value = "code 번호", required = true ) @RequestBody CodeRequest codeRequest) {
	
	 */
	public static final Logger logger = LoggerFactory.getLogger(TeamController.class);
	
	@Autowired
	JwtTokenService jwtTokenService;
	
	@Autowired
	MemberRepo memberRepo;
	
	@Autowired
	TeamRepo teamRepo;
	
	@Autowired
	RepositoryService repositoryService;
	
	@ApiOperation(value="팀장으로 공모전에 지원할시 헤더에는 back end에서 발행한 토큰을 이용", notes="리턴 값으로 succ, fail을 출력한다.")
	@GetMapping(value = "/checkRepositoryName/{repoName}")
 	public ResponseEntity<CommonResponse> checkRepositoryName(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "등록하고자하는 repository name", required = true) @PathVariable String repoName) {
		if(!jwtTokenService.validateToken(accessToken)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Member member = memberRepo.findByEmail(jwtTokenService.getUserPk(accessToken)).orElse(null);
		if(member == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		repoName = repoName.trim();
		boolean check = repositoryService.checkRepositoryName(repoName, member.getGithub(), member.getToken());
		if(!check) {
			return new ResponseEntity<CommonResponse>(new CommonResponse(-1, "checkRepositoryName", CommonResponse.FAIL), HttpStatus.OK);
		}
		return new ResponseEntity<CommonResponse>(new CommonResponse(0, "checkRepositoryName", CommonResponse.SUCC), HttpStatus.OK);
	}
	
	@ApiOperation(value="팀장으로 공모전에 지원할시", notes="리턴 값으로 succ, fail을 출력한다.")
	@PostMapping(value = "/applyAsLeader")
	public ResponseEntity<SingleResult<String>> applyAsLeader(@ApiParam(value = "github 저장소 이름, 백엔드 토큰, 공모전 아이디", required = true) @RequestBody ApplyAsLeaderRequest applyRequest){
		if(applyRequest.getBoard_id() == null  || applyRequest.getGithub_repo_name() == null 
				|| !jwtTokenService.validateToken( applyRequest.getAccess_token())) {
			//board_id나 repo_name이 null이거나 유효하지 않는 토큰이면 request가 올바르지 않다고. 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Member leader = memberRepo.findByEmail(jwtTokenService.getUserPk(applyRequest.getAccess_token())).orElse(null);
		if(leader == null) {
			//토큰에 대한 정보가 없으면
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		logger.info("Github Token : " + leader.getToken());
		//이때까지 토큰이 올바르고, 회원이 존재하는 경우.
		
		Repository repository = repositoryService.createRepository(applyRequest.getGithub_repo_name(), leader.getToken()); 
		if(repository == null) {
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		//여기까지 Repository 생성.
		
		Team team = new Team();
		team.setBoardId(applyRequest.getBoard_id());
		SimpleDateFormat dateFomat = new SimpleDateFormat ( "yyyy-MM-dd");
		team.setTeamDate(dateFomat.format(System.currentTimeMillis()));
		team.setTeamState(Team.STATE_RUN);
		team.setTeamLeader(leader.getEmail());
		team.setTeamMemberNum(1);
		team.setGithubRepoUrl(repository.getHtml_url());
		teamRepo.save(team);
		//공모전에 대한 team을 생성하는 로직
		
		SingleResult<String> res = new SingleResult<>(0,"by createRepo",CommonResponse.SUCC);
		res.setData(repository.getHtml_url());
		return new ResponseEntity<SingleResult<String>>(res, HttpStatus.OK);
	}
	
	@ApiOperation(value="팀장일때 Member를 팀원으로 등록하고자 할때", notes="리턴 값으로 succ, fail을 출력한다.")
	@PostMapping(value = "/addTeamMember")
	public ResponseEntity<CommonResponse> addTeamMember(@ApiParam(value = "백엔드 토큰,추가하고자 할 team id ,추가하고자 할 member email", required = true) @RequestBody AddTeamMemberRequest addTeamMemberRequest){
		logger.info(addTeamMemberRequest.toString());
		String accessToken = addTeamMemberRequest.getAccessToken();
		if(!jwtTokenService.validateToken(accessToken)) {
			new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String email = jwtTokenService.getUserPk(accessToken);
		Member owner = memberRepo.findByEmail(email).orElse(null);
		logger.info(owner.toString());
		
		if(owner.getGithub() == null) {
			return new ResponseEntity<>(new CommonResponse(-1,"github 등록을 먼저", CommonResponse.FAIL), HttpStatus.OK);
		}
		
		//team_id와 member_email는 유효하다고 가정하자.
		Team team = teamRepo.findById(addTeamMemberRequest.getTeamId()).orElse(null);
		
		if(team == null) {
			return new ResponseEntity<>(new CommonResponse(-1,"team 등록을 먼저", CommonResponse.FAIL), HttpStatus.OK);
		}
		
		boolean isAddedMember = repositoryService.addTeamMember(owner.getToken(), owner.getGithub(), team.getGithubRepoUrl(), addTeamMemberRequest.getMemberEmail());
		if(!isAddedMember) {
			return new ResponseEntity<>(new CommonResponse(-1,"유저를 팀에 추가할 수 없습니다.", CommonResponse.FAIL), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(new CommonResponse(0,"github Repository에 추가되었습니다.", CommonResponse.SUCC), HttpStatus.OK);
		}
	}

	

}
