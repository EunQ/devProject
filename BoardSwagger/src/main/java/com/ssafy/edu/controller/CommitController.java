package com.ssafy.edu.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.Board;
import com.ssafy.edu.dto.Comment;
import com.ssafy.edu.dto.Commit;
import com.ssafy.edu.dto.Team;
import com.ssafy.edu.help.BoardNumberResult;
import com.ssafy.edu.help.CommentNumberResult;
import com.ssafy.edu.jpa.TeamRepo;
import com.ssafy.edu.response.CommonResponse;
import com.ssafy.edu.response.SingleResult;
import com.ssafy.edu.service.RepositoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/commit")
@Api(value = "CommitController commit관련 repo는 전부 public 이라 가정.", description = "댓글")
@CrossOrigin("*")
public class CommitController {

	public static final Logger logger = LoggerFactory.getLogger(CommitController.class);
	
	@Autowired
	private TeamRepo teamRepo;
	
	@Autowired
	private RepositoryService repositoryService; 
	
	@ApiOperation(value = "마지막 commit 보기", notes = "해당 team의 repository의 마지막 commit을 리턴")
	@GetMapping(value = "/lastCommit/{teamId}")
	public ResponseEntity<SingleResult<Commit>> getLastCommit(
			@ApiParam(value = "해당 팀 id", required = true) @PathVariable int teamId
			) {
		logger.info("================getLastCommit================\t" + new Date());
		Team team =  teamRepo.findByTeamId(teamId).orElse(null);
		if(team == null || team.getGithubRepoUrl() == null) {
			return SingleResult.makeResponseEntity(-1, "팀이 없거나 팀 Repository가 없습니다.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		
		Commit lastCommit = repositoryService.getLastCommit(team.getGithubRepoUrl());
		if(lastCommit == null) {
			return SingleResult.makeResponseEntity(-1, "commit이 없습니다.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		return SingleResult.makeResponseEntity(0, "최근 commit입니다.", CommonResponse.SUCC, lastCommit, HttpStatus.OK);
	}
	
	@ApiOperation(value = "마지막 commit들 보기", notes = "해당 team의 repository의 마지막 commit을 리턴")
	@GetMapping(value = "/lastCommits/{teamId}/{listNum}")
	public ResponseEntity<SingleResult<List<Commit>>> getLastCommitNum(
			@ApiParam(value = "해당 팀 id", required = true) @PathVariable int teamId,
			@ApiParam(value = "해당 Commit 개수", required = true) @PathVariable int listNum
			) {
		logger.info("================getLastCommitNum================\t" + new Date());
		Team team =  teamRepo.findByTeamId(teamId).orElse(null);
		if(team == null || team.getGithubRepoUrl() == null) {
			return SingleResult.makeResponseEntity(-1, "팀이 없거나 팀 Repository가 없습니다.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		
		List<Commit> lastCommit = repositoryService.getLastCommit(team.getGithubRepoUrl(), listNum);
		if(lastCommit == null) {
			return SingleResult.makeResponseEntity(-1, "commit이 없습니다.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		return SingleResult.makeResponseEntity(0, "최근 commit입니다.", CommonResponse.SUCC, lastCommit, HttpStatus.OK);
	}
	
	@ApiOperation(value = "마지막 commit 보기", notes = "해당 team의 repository의 마지막 commit을 리턴")
	@GetMapping(value = "/lastCommit/url")
	public ResponseEntity<SingleResult<Commit>> getLastCommitByUrl(
			@ApiParam(value = "해당 url", required = true) @RequestParam String url
			) {
		logger.info("================getLastCommitByUrl================\t" + new Date());
		
		Commit lastCommit = repositoryService.getLastCommit(url);
		if(lastCommit == null) {
			return SingleResult.makeResponseEntity(-1, "commit이 없습니다.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		return SingleResult.makeResponseEntity(0, "최근 commit입니다.", CommonResponse.SUCC, lastCommit, HttpStatus.OK);
	}
	
	@ApiOperation(value = "마지막 commit들 보기", notes = "해당 team의 repository의 마지막 commit을 리턴")
	@GetMapping(value = "/lastCommits/url/{listNum}")
	public ResponseEntity<SingleResult<List<Commit>>> getLastCommitNumByUrl(
			@ApiParam(value = "해당 url", required = true) @RequestParam String url,
			@ApiParam(value = "해당 Commit 개수", required = true) @PathVariable int listNum
			) {
		logger.info("================getLastCommitNumByUrl================\t" + new Date());
		
		List<Commit> lastCommit = repositoryService.getLastCommit(url, listNum);
		if(lastCommit == null) {
			return SingleResult.makeResponseEntity(-1, "commit이 없습니다.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		return SingleResult.makeResponseEntity(0, "최근 commit입니다.", CommonResponse.SUCC, lastCommit, HttpStatus.OK);
	}
	
	@ApiOperation(value = "commit user들 보기", notes = "해당 team의 repository의 유저들의 커밋들을 보기")
	@GetMapping(value = "/users/{teamId}")
	public ResponseEntity<SingleResult<HashMap<String, Integer>>> getUsersCommits(
			@ApiParam(value = "해당 teamId", required = true) @PathVariable int teamId
			) {
		logger.info("================getUsersCommits================\t" + new Date());
		Team team = teamRepo.findById(teamId).orElse(null);
		if(team == null || team.getGithubRepoUrl() == null) {
			return SingleResult.makeResponseEntity(-1, "팀이 없거나 팀 Repository가 없습니다.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		HashMap<String, Integer> res = repositoryService.getUsersCommits(team.getGithubRepoUrl());
		if(res == null) {
			return SingleResult.makeResponseEntity(-1, "commit이 없습니다.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		return SingleResult.makeResponseEntity(0, "{usr , cnt} List 입니다.", CommonResponse.SUCC, res, HttpStatus.OK);
	}
	
	@ApiOperation(value = "commit user들 보기", notes = "해당 team의 repository의 유저들의 커밋들을 보기")
	@GetMapping(value = "/users/url")
	public ResponseEntity<SingleResult<HashMap<String, Integer>>> getUsersCommitsByUrl(
			@ApiParam(value = "해당 url", required = true) @RequestParam String url
			) {
		logger.info("================getUsersCommitsByUrl================\t" + new Date());
		HashMap<String, Integer> res = repositoryService.getUsersCommits(url);
		if(res == null) {
			return SingleResult.makeResponseEntity(-1, "commit이 없습니다.", CommonResponse.FAIL, null, HttpStatus.BAD_REQUEST);
		}
		return SingleResult.makeResponseEntity(0, "{usr , cnt} List 입니다.", CommonResponse.SUCC, res, HttpStatus.OK);
	}
}
