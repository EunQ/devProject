package com.ssafy.edu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.Apply;
import com.ssafy.edu.dto.Member;
import com.ssafy.edu.dto.Repository;
import com.ssafy.edu.dto.Team;
import com.ssafy.edu.dto.TeamMember;
import com.ssafy.edu.jpa.ApplyRepo;
import com.ssafy.edu.jpa.BoardRepo;
import com.ssafy.edu.jpa.MemberRepo;
import com.ssafy.edu.jpa.TeamMemberRepo;
import com.ssafy.edu.jpa.TeamRepo;
import com.ssafy.edu.request.CreateRepositoryRequest;
import com.ssafy.edu.request.CreateTeamRequest;
import com.ssafy.edu.response.CommonResponse;
import com.ssafy.edu.response.SingleResult;
import com.ssafy.edu.service.CrawlService;
import com.ssafy.edu.service.HostService;
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
	
	public static final Logger logger = LoggerFactory.getLogger(TeamController.class);
	
	@Autowired
	JwtTokenService jwtTokenService;
	
	@Autowired
	HostService sponsorService;
	
	@Autowired
	MemberRepo memberRepo;
	
	@Autowired
	TeamRepo teamRepo;
	
	@Autowired
	TeamMemberRepo teamMemberRepo;
	
	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
	ApplyRepo applyRepo;
	
	@Autowired
	BoardRepo boardRepo;

	@ApiOperation(value="github repository의 이름을 체크", notes="리턴 값으로 succ, fail을 출력한다.")
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
	
	@ApiOperation(value="github repository 팀장이 생성하는 부분.", notes="팀내 페이지에서 사용가능, 리턴 값으로 repository 객체를 리턴")
	@PostMapping(value = "/repo")
 	public ResponseEntity<SingleResult<Repository>> createRepository(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "현재 팀 id,  등록하고자하는 repository name", required = true) @RequestBody CreateRepositoryRequest request) {
		if(!jwtTokenService.validateToken(accessToken)) {
			return SingleResult.makeResponseEntity(-1, "엑세스 토큰이 유효하지않음.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		Member member = memberRepo.findByEmail(jwtTokenService.getUserPk(accessToken)).orElse(null);
		if(member == null) {
			return SingleResult.makeResponseEntity(-1, "사용자가 존재하지 않음.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		if(member.getGithub() == null) {
			return SingleResult.makeResponseEntity(-1, "사용자의 깃헙 인증이 필요함.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		Team team  = teamRepo.findById(request.getTeamId()).orElse(null);
		if(team == null) {
			return SingleResult.makeResponseEntity(-1, "팀이 존재하지 않음.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		TeamMember tm = teamMemberRepo.findByEmailAndTeam(member.getEmail(), team).orElse(null);
		if(team == null || !tm.isLeader()) {
			return SingleResult.makeResponseEntity(-1, "팀장이 아닙니다.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		//여기까지 유효성 검사
		String repoName = request.getRepoName().trim();
		boolean check = repositoryService.checkRepositoryName(repoName, member.getGithub(), member.getToken());
		if(!check) {
			return SingleResult.makeResponseEntity(-1, "Repository 이름이 유효하지 않음.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		Repository res = repositoryService.createRepository(repoName, member.getToken());
		team.setGithubRepoUrl(res.getHtml_url());
		teamRepo.save(team);
		teamRepo.flush();
		logger.info(team.toString());
		return SingleResult.makeResponseEntity(0, "성공적으로 Repository 생성함", CommonResponse.SUCC, res, HttpStatus.OK);
	}
	

	@ApiOperation(value="팀을 만들때", notes="리턴 값으로 succ, fail을 출력한다.")
	@PostMapping(value = "/create")
	public ResponseEntity<SingleResult<Team>> unifyCreateTeam(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			 @ApiParam(value = "만들고자 하는 TeamName", required = true) @RequestBody CreateTeamRequest request){
		logger.info("unifyCreateTeam " + request.toString()); 
		if(!jwtTokenService.validateToken(accessToken)) {
			//board_id나 repo_name이 null이거나 유효하지 않는 토큰이면 request가 올바르지 않다고. 
			return SingleResult.makeResponseEntity(-1, "token 이 유효하지 않음.", CommonResponse.FAIL, null,HttpStatus.BAD_REQUEST);
		}
		String leaderEmail = jwtTokenService.getUserPk(accessToken);
		Member leader = memberRepo.findByEmail(leaderEmail).orElse(null);
		if(leader == null) {
			//유저에 대한 정보가 없으면.
			return SingleResult.makeResponseEntity(-1, "token email이 존재하지 않음", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		logger.info("Github Token : " + leader.getToken());
		//이때까지 토큰이 올바르고, 회원이 존재하는 경우.
		Team newTeam = new Team();
		SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd");
		newTeam.setTeamDate(fomat.format(new Date()));
		newTeam.setTeamMemberNum(1);
		newTeam.setTeamName(request.getTeamName());
		newTeam.setTeamState(Team.STATE_READY);
		teamRepo.save(newTeam);
		teamRepo.flush();
		
		logger.info(" ---added new team--- "+newTeam.toString());
		
		ArrayList<TeamMember> memberList = new ArrayList<>();
		String[] requestMemberList = request.getMemberList();
		for(String memberEmail : requestMemberList) {
			memberList.add(new TeamMember(null,memberEmail, newTeam , TeamMember.ROLE_MEMBER, TeamMember.ACCEPT_STATE_READY ));
		}
		memberList.add(new TeamMember(null, leaderEmail, newTeam, TeamMember.ROLE_LEADER, TeamMember.ACCEPT_STATE_DONE));
		teamMemberRepo.saveAll(memberList);
		teamMemberRepo.flush();
		logger.info("---added team member---");
		
		SimpleDateFormat dateFomat = new SimpleDateFormat("yyyy-MM-dd");
		Apply apply = new Apply();
		apply.setApplyDate(dateFomat.format(new Date()));
		apply.setTeam(newTeam);
		apply.setBoardId(request.getBoardId());
		apply.setApplyInfo(request.getInfo());
		apply.setIdea(request.getIdea());
		applyRepo.save(apply);
		applyRepo.flush();
		logger.info("---added team apply---");
		
		return SingleResult.makeResponseEntity(0, "팀이 정상적으로 생성", CommonResponse.SUCC, newTeam,HttpStatus.OK);
	}
	
//	
//	@ApiOperation(value="팀을 만들때", notes="리턴 값으로 succ, fail을 출력한다.")
//	@PostMapping(value = "/createTeam/{teamName}")
//	public ResponseEntity<SingleResult<Team>> createTeam(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
//			 @ApiParam(value = "만들고자 하는 TeamName", required = true) @PathVariable String teamName){
//		
//		logger.info("createTeam teamName -- " + teamName); 
//		if(!jwtTokenService.validateToken(accessToken)) {
//			//board_id나 repo_name이 null이거나 유효하지 않는 토큰이면 request가 올바르지 않다고. 
//			return SingleResult.makeResponseEntity(-1, "token 이 유효하지 않음.", CommonResponse.FAIL, null,HttpStatus.BAD_REQUEST);
//		}
//		String leaderEmail = jwtTokenService.getUserPk(accessToken);
//		Member leader = memberRepo.findByEmail(leaderEmail).orElse(null);
//		if(leader == null) {
//			//유저에 대한 정보가 없으면.
//			return SingleResult.makeResponseEntity(-1, "token email이 존재하지 않음", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
//		}
//		logger.info("Github Token : " + leader.getToken());
//		//이때까지 토큰이 올바르고, 회원이 존재하는 경우.
//		Team newTeam = new Team();
//		SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd");
//		newTeam.setTeamDate(fomat.format(new Date()));
//		newTeam.setTeamMemberNum(1);
//		newTeam.setTeamName(teamName);
//		newTeam.setTeamState(Team.STATE_READY);
//		teamRepo.save(newTeam);
//		teamRepo.flush();
//		
//		logger.info(newTeam.toString());
//		
//		TeamMember newTm = new TeamMember();
//		newTm.setAccept(1);
//		newTm.setEmail(leaderEmail);
//		newTm.setRole(TeamMember.ROLE_LEADER);
//		newTm.setTeam(newTeam);
//		
//		teamMemberRepo.save(newTm);
//		teamMemberRepo.flush();
//		return SingleResult.makeResponseEntity(0, "팀이 정상적으로 생성", CommonResponse.SUCC, newTeam,HttpStatus.OK);
//	}
	

	@ApiOperation(value="현재 팀 표시", notes="리턴 값으로 내가 속한 팀들을 전부 출력 TeamMember class List")
	@GetMapping(value = "/{teamId}")
	public ResponseEntity<SingleResult<Team>> findTeam(@ApiParam(value = "알고자 하는 teamId") @PathVariable Integer teamId){
		
		logger.info("/{teamId} -- " + teamId); 
		Team team = teamRepo.findByTeamId(teamId).orElse(null);
		if(team == null) {
			return SingleResult.makeResponseEntity(-1, "해당 팀이 없습니다.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		return SingleResult.makeResponseEntity(0, "해당 id 팀", CommonResponse.SUCC, team, HttpStatus.OK);
	}
	
	@ApiOperation(value="내가 속한 팀들 전부 표시", notes="리턴 값으로 내가 속한 팀들을 전부 출력 TeamMember class List")
	@GetMapping(value = "/user/teams")
	public ResponseEntity<List<TeamMember>> findAllTeam(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken){
		
		logger.info("/user/teams -- " + accessToken); 
		if(!jwtTokenService.validateToken(accessToken)) {
			//board_id나 repo_name이 null이거나 유효하지 않는 토큰이면 request가 올바르지 않다고. 
			logger.info("token error");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String memberEmail = jwtTokenService.getUserPk(accessToken);
		Member member = memberRepo.findByEmail(memberEmail).orElse(null);
		if(member == null) {
			//유저에 대한 정보가 없으면.
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
//		List<TeamMember> tms = teamMemberRepo.findAllByEmail(memberEmail);
		List<TeamMember> resData = teamMemberRepo.findAllByEmail(memberEmail);
//		logger.info(resData.get(0).toString());
		return new ResponseEntity<>(resData, HttpStatus.OK);
	}
	
//	//apply team
//	@ApiOperation(value="해당 공모전에 지원하기", notes="리턴 값으로 succ/fail출력")
//	@PostMapping(value = "/apply")
//	public ResponseEntity<CommonResponse> applyToBoard(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken ,
//			@ApiParam(value = "지원하고자 할 Team Id, board Id, 지원서 text", required = true)  @RequestBody ApplyBoardRequest applyBoardRequest){
//		logger.info("-------------------applyToBoard-----------------");
//		logger.info(applyBoardRequest.toString()); 
//		if(!jwtTokenService.validateToken(accessToken)) {
//			//board_id나 repo_name이 null이거나 유효하지 않는 토큰이면 request가 올바르지 않다고. 
//			logger.info("token error");
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		String leaderEmail = jwtTokenService.getUserPk(accessToken);
//		Member leader = memberRepo.findByEmail(leaderEmail).orElse(null);
//		Board board = boardRepo.findById(applyBoardRequest.getBoardId()).orElse(null);
//		
//		Team team = teamRepo.findById(applyBoardRequest.getTeamId()).orElse(null);
//		
//		if(leader == null || team == null || board == null) {
//			//유저에 대한 정보가 없으면.
//			return CommonResponse.makeResponseEntity(-1, "유효한 회원 email이 아니거나 팀, 공모전이 없습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
//		}
//		TeamMember tm = teamMemberRepo.findByEmailAndTeam(leaderEmail, team).orElse(null);
//		if(tm == null || !tm.isLeader()) {
//			return CommonResponse.makeResponseEntity(-1, "팀장 email이 아닙니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
//		}
//		Apply preApply = applyRepo.findByBoardIdAndTeamId(applyBoardRequest.getBoardId(), applyBoardRequest.getTeamId()).orElse(null);
//		if(preApply != null) {
//			return CommonResponse.makeResponseEntity(-1, "이미 지원했습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
//		}
//		SimpleDateFormat fomatDate = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			Date applyLimitDate = fomatDate.parse(board.getApplyEnd());
//			if(applyLimitDate.before(new Date())) {
//				return CommonResponse.makeResponseEntity(-1, "지원 가능한 날짜가 지났습니다.", CommonResponse.FAIL, HttpStatus.BAD_REQUEST);
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		Apply apply = new Apply();
//		apply.setApplyDate(fomatDate.format(new Date()));
//		apply.setApplyInfo(applyBoardRequest.getInfo());
//		apply.setBoardId(applyBoardRequest.getBoardId());
//		apply.setTeamId(applyBoardRequest.getTeamId());
//		logger.info("apply - " + apply.toString());
//		applyRepo.save(apply);
//		applyRepo.flush();
//		return CommonResponse.makeResponseEntity(0, "정상적으로 지원했습니다.", CommonResponse.SUCC, HttpStatus.BAD_REQUEST);
//	}
	
	@ApiOperation(value="팀장일때 Team상태를 확정하는 부분.", notes="리턴 값으로 succ, fail을 출력한다.")
	@PatchMapping(value = "/confirm/{teamId}")
	public ResponseEntity<CommonResponse> confirmTeam(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "기존의 Team Id", required = true) @PathVariable int teamId) {
		logger.info("----confirmTeam----  " + teamId);
		if(!jwtTokenService.validateToken(accessToken)) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 access-token", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		String leaderEmail = jwtTokenService.getUserPk(accessToken);
		Team leaderTeam  = teamRepo.findById(teamId).orElse(null);
		if(leaderTeam == null) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 teamId", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		
		TeamMember tm = teamMemberRepo.findByEmailAndTeam(leaderEmail, leaderTeam).orElse(null);
		
		if(tm == null || !tm.isLeader() ) {
			return new ResponseEntity<>(new CommonResponse(-1,"올바르지 않는 Leader, 팀이 없거나 팀장이 아님", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("before change Team's State");
		leaderTeam.setTeamState(Team.STATE_RUN);
		teamRepo.save(leaderTeam);
		teamRepo.flush();
		
		//여기서 아직 accept 요청이 남은 teamMember는 삭제한다.
		List<TeamMember> tms = teamMemberRepo.findAllByTeamAndAccept(leaderTeam, TeamMember.ACCEPT_STATE_READY);
		logger.info(StringUtil.join(tms, " | "));
		for(int i=0;i<tms.size();i++) {
			teamMemberRepo.delete(tms.get(i));
		}
		teamMemberRepo.flush();			
		return new ResponseEntity<>(new CommonResponse(0,"confirm team succ", CommonResponse.SUCC), HttpStatus.OK);
	}
	
	@ApiOperation(value="팀장일때 Team 삭제.", notes="리턴 값으로 succ, fail을 출력한다.")
	@DeleteMapping(value = "/confirm/{teamId}")
	public ResponseEntity<CommonResponse> confirmDeleteTeam(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "기존의 Team Id", required = true) @PathVariable int teamId) {
		logger.info("--------confirmDeleteTeam--------- teamid : " + teamId);
		if(!jwtTokenService.validateToken(accessToken)) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 access-token", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		String leaderEmail = jwtTokenService.getUserPk(accessToken);
		Team leaderTeam  = teamRepo.findById(teamId).orElse(null);
		if(leaderTeam == null ) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 teamId or leaderEmail", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		TeamMember tm = teamMemberRepo.findByEmailAndTeam(leaderEmail, leaderTeam).orElse(null);
		
		if(tm == null || !tm.isLeader() ) {
			return new ResponseEntity<>(new CommonResponse(-1,"올바르지 않는 Leader, 팀이 없거나 팀장이 아님", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		teamRepo.delete(leaderTeam); //자동적으로 teamMember, apply table instance 삭제됨
		teamRepo.flush();
		logger.info("----confirmDeleteTeam   정상적으로 팀 삭제----");
		return new ResponseEntity<>(new CommonResponse(0,"confirm delete team succ", CommonResponse.SUCC), HttpStatus.OK);
	}
	
	@ApiOperation(value="팀장일때 Team을 제거하는 부분, 팀이 아직 Ready(공모전 시작전)상태일때 제거가능", notes="team 상태가 Ready일 경우만 삭제가능. 리턴 값으로 succ, fail을 출력한다.")
	@DeleteMapping(value = "/delete/{teamId}")
	public ResponseEntity<CommonResponse> deleteTeam(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "기존의 Team Id", required = true) @PathVariable int teamId) {
		logger.info("----deleteTeam----");
		if(!jwtTokenService.validateToken(accessToken)) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 access-token", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		String leaderEmail = jwtTokenService.getUserPk(accessToken);
		Team leaderTeam  = teamRepo.findById(teamId).orElse(null);
		if(leaderTeam == null) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 teamId", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		
		TeamMember tm = teamMemberRepo.findByEmailAndTeam(leaderEmail, leaderTeam).orElse(null);
		
		if(tm == null || !tm.isLeader() ) {
			return new ResponseEntity<>(new CommonResponse(-1,"올바르지 않는 Leader, 팀이 없거나 팀장이 아님", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("before delete Team's State");
		teamRepo.delete(leaderTeam);
		teamRepo.flush();
		
		return new ResponseEntity<>(new CommonResponse(0,"confirm team succ", CommonResponse.SUCC), HttpStatus.OK);
	}
	

	@ApiOperation(value="팀장일때 Member를 추가하는 부분", notes="리턴 값으로 succ, fail을 출력한다.")
	@PostMapping(value = "/intoTeam/{teamId}/{email}")
	public ResponseEntity<CommonResponse> intoTeam(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "기존의 Team Id", required = true) @PathVariable int teamId, @ApiParam(value = "등록하고자하는 Member email", required = true) @PathVariable String email) {
		logger.info("----intoTeam----");
		if(!jwtTokenService.validateToken(accessToken)) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 access-token", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		String leaderEmail = jwtTokenService.getUserPk(accessToken);
		Team leaderTeam  = teamRepo.findById(teamId).orElse(null);
		if(leaderTeam == null) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 teamId", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		
		TeamMember tm = teamMemberRepo.findByEmailAndTeam(leaderEmail, leaderTeam).orElse(null);
		
		if(tm == null || !tm.isLeader() ) {
			return new ResponseEntity<>(new CommonResponse(-1,"올바르지 않는 Leader, 팀이 없거나 팀장이 아님", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		Member addMember = memberRepo.findByEmail(email).orElse(null);
		if(addMember == null ) {
			return new ResponseEntity<>(new CommonResponse(-1,"올바르지 않는 Member, 팀원이 존재하지 않음.", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		if(addMember.getEmail().equals(leaderEmail)) {
			return new ResponseEntity<>(new CommonResponse(-1,"자기 자신은 추가될 수 없음.", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("add TeamMember ..insert");
		TeamMember newTm = new TeamMember();
		newTm.setAccept(0);
		newTm.setEmail(email);
		newTm.setRole(TeamMember.ROLE_MEMBER);
		newTm.setTeam(leaderTeam);
		teamMemberRepo.save(newTm);
		teamMemberRepo.flush();
		return new ResponseEntity<>(new CommonResponse(0,"into Team succ", CommonResponse.SUCC), HttpStatus.OK);
	}
	

	@ApiOperation(value="팀장일때 Member를 제거하는 부분", notes="리턴 값으로 succ, fail을 출력한다.")
	@DeleteMapping(value = "/outTeam/{teamId}/{email}")
	public ResponseEntity<CommonResponse> outTeam(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "기존의 Team Id", required = true) @PathVariable int teamId, @ApiParam(value = "제거하고자하는 Member email", required = true) @PathVariable String email){
		if(!jwtTokenService.validateToken(accessToken)) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 access-token", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		Team leaderTeam  = teamRepo.findById(teamId).orElse(null);
		if(leaderTeam == null) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 teamId", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		String leaderEmail = jwtTokenService.getUserPk(accessToken);
		TeamMember leaderTm = teamMemberRepo.findByEmailAndTeam(leaderEmail, leaderTeam).orElse(null);
		if(leaderTm == null || !leaderTm.isLeader() ) {
			return new ResponseEntity<>(new CommonResponse(-1,"올바르지 않는 Leader, 팀이 없거나 팀장이 아님", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		Member addMember = memberRepo.findByEmail(email).orElse(null);
		if(addMember == null ) {
			return new ResponseEntity<>(new CommonResponse(-1,"올바르지 않는 Member, 팀원이 존재하지 않음.", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		if(addMember.getEmail().equals(leaderEmail)) {
			return new ResponseEntity<>(new CommonResponse(-1,"자기 자신은 추가될 수 없음.", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		TeamMember memberTm = teamMemberRepo.findByEmailAndTeam(email, leaderTeam).orElse(null);
		if(memberTm == null) {
			return new ResponseEntity<>(new CommonResponse(-1,"올바르지 않는 Member, 팀원이 팀에 존재하지 않음.", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		if(memberTm.getAccept() == 1) {
			//수락된 맴버였으면 팀원숫자에서 제거
			leaderTeam.subMember();
			teamRepo.save(leaderTeam);
			teamRepo.flush();
		}
		teamMemberRepo.delete(memberTm);
		teamMemberRepo.flush();
		return new ResponseEntity<>(new CommonResponse(0,"out Team succ", CommonResponse.SUCC), HttpStatus.OK);
	}
	
	@ApiOperation(value="팀원으로서 accept 요청들", notes="리턴 값으로 요청받은 TeamMember entries를 리턴")
	@GetMapping(value = "/accept")
	public ResponseEntity<List<TeamMember>> findAllTeamMemberRequest(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken) {
		if(!jwtTokenService.validateToken(accessToken)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String memberEmail = jwtTokenService.getUserPk(accessToken);
		
		List<TeamMember> dates =  teamMemberRepo.findAllByEmailAndAccept(memberEmail, 0);
			
		return new ResponseEntity<List<TeamMember>>(dates, HttpStatus.OK);
	}
	
	
	@ApiOperation(value="팀원으로서 accept부분을 하는 부분.", notes="리턴 값으로 succ, fail을 출력한다.")
	@PatchMapping(value = "/accept/{teamMemberId}")
	public ResponseEntity<CommonResponse> acceptTeamMember(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "teamMember id [팀원의 정보가 저장되어있는 id]", required = true) @PathVariable int teamMemberId) {
		if(!jwtTokenService.validateToken(accessToken)) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 access-token", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		String memberEmail = jwtTokenService.getUserPk(accessToken);
		TeamMember tm = teamMemberRepo.findById(teamMemberId).orElse(null);
		
		if(tm == null) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 team_member_id", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}

		if(!tm.getEmail().equals(memberEmail)) {
			return new ResponseEntity<>(new CommonResponse(-1,"권한이 없는 email", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		
		tm.setAccept(1);
		teamMemberRepo.save(tm);
		teamMemberRepo.flush();
		
		Team team = tm.getTeam();
		team.addMember();
		teamRepo.save(team);
		teamRepo.flush();
		//team member num을 하나 늘린다.
		
		return new ResponseEntity<>(new CommonResponse(0,"accept Team succ", CommonResponse.SUCC), HttpStatus.OK);
	}
	@ApiOperation(value="팀원으로서 accept부분을 하는 부분.", notes="리턴 값으로 succ, fail을 출력한다.")
	@DeleteMapping(value = "/refuse/{teamMemberId}")
	public ResponseEntity<CommonResponse> refuseTeamMember(@ApiParam(value = "back-end access token", required = true) @RequestHeader("x-access-token") String accessToken,
			@ApiParam(value = "teamMember id [팀원의 정보가 저장되어있는 id]", required = true) @PathVariable int teamMemberId) {
		if(!jwtTokenService.validateToken(accessToken)) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 access-token", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		String memberEmail = jwtTokenService.getUserPk(accessToken);
		TeamMember tm = teamMemberRepo.findById(teamMemberId).orElse(null);
		
		if(tm == null) {
			return new ResponseEntity<>(new CommonResponse(-1,"유효하지 않는 team_member_id", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}

		if(!tm.getEmail().equals(memberEmail)) {
			return new ResponseEntity<>(new CommonResponse(-1,"권한이 없는 email", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		
		teamMemberRepo.delete(tm);
		teamMemberRepo.flush();
		
		
		return new ResponseEntity<>(new CommonResponse(0,"accept Team succ", CommonResponse.SUCC), HttpStatus.OK);
	}
	
	@ApiOperation(value="해당 팀에대한 요청이 승인된 TeamMember를 리턴함 ", notes="팀원 요청에 대한 리턴 값으로 해당팀에 대한 TeamMember List를 출력.")
	@PatchMapping(value = "/members/{teamId}")
	public ResponseEntity<List<TeamMember>> findTeamMember(@ApiParam(value = "team id [팀 id]", required = true) @PathVariable int teamId) {
		Team team = teamRepo.findById(teamId).orElse(null);
		if(team == null) {
			return new ResponseEntity<>( HttpStatus.OK);
		}
		List<TeamMember> dates = teamMemberRepo.findAllByTeamAndAccept(team, TeamMember.ACCEPT_STATE_DONE);
		return new ResponseEntity<>(dates, HttpStatus.OK);
	}
	
	@ApiOperation(value="해당 boardId에 대한 팀들 apply의 정보 출력", notes="해당 공모전 ID에 대한 신청한 팀들의 apply 정보를 부른다.")
	@GetMapping(value = "/apply/{boardId}")
	public ResponseEntity<List<Apply>> findAllApplyByBoardId(@ApiParam(value = "해당 공모전 id", required = true) @PathVariable int boardId) {
		
		logger.info("-----------------------findAllApplyByBoardId--------------------------");
		return new ResponseEntity<>(applyRepo.findAllByBoardId(boardId), HttpStatus.OK);
	}
	
	@ApiOperation(value = "repository의 readme 추출")
	@GetMapping(value = "/getMarkDown/{user}/{repository}" )
	public ResponseEntity<String> getMarkDown(@PathVariable String user, @PathVariable String repository){
		String url = "https://github.com/" + user + "/" + repository;
		// repository 정보 가져온 후 html_url 추출 필요
		CrawlService crawl = new CrawlService();
		String readMe = crawl.goCrawling(url);
		
		return new ResponseEntity<String>(readMe, HttpStatus.OK);
	}
}
