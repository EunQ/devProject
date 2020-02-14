package com.ssafy.edu.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.Board;
import com.ssafy.edu.dto.Comment;
import com.ssafy.edu.help.BoardNumberResult;
import com.ssafy.edu.help.CommentNumberResult;
import com.ssafy.edu.jpa.BoardRepo;
import com.ssafy.edu.jpa.CommentRepo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value = "CommentController", description = "댓글")
@CrossOrigin("*")
public class CommentController {
	
	public static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	CommentRepo commentRepo;
	
	@Autowired
	BoardRepo boardRepo;
	
	@ApiOperation(value = "댓글 추가", response = BoardNumberResult.class)
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public ResponseEntity<CommentNumberResult> addComment(@RequestBody Comment comment) throws Exception {
		System.out.println("================addComment================\t" + new Date());

		CommentNumberResult cnr = new CommentNumberResult();
		Board board = boardRepo.findById(comment.getBoardId()).orElse(null);
		cnr.setName("addComment");
		cnr.setNumber(comment.getBoardId());

		if (board == null) {
			cnr.setState("fail");
			return new ResponseEntity<CommentNumberResult>(cnr, HttpStatus.BAD_REQUEST);
		}
		cnr.setState("succ");
		commentRepo.save(comment);

		return new ResponseEntity<CommentNumberResult>(cnr, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 목록", response = List.class)
	@RequestMapping(value = "/getComment/{boardId}", method = RequestMethod.GET)
	public ResponseEntity<List<Comment>> getComment(@PathVariable int boardId) throws Exception {

		System.out.println("================getComment================\t" + new Date());

		List<Comment> commentList = commentRepo.findAllByBoardId(boardId);

		if (commentList.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Comment>>(commentList, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 삭제", response = BoardNumberResult.class)
	@RequestMapping(value = "/deleteComment/{cnum}", method = RequestMethod.DELETE)
	public ResponseEntity<CommentNumberResult> deleteComment(@PathVariable int cnum) throws Exception {
		System.out.println("================deleteComment================\t" + new Date());

		CommentNumberResult cnr = new CommentNumberResult();
		Comment comment = commentRepo.findById(cnum).orElse(null);
		cnr.setName("deleteComment");
		cnr.setNumber(cnum);

		if (comment == null) {
			cnr.setState("fail");
			return new ResponseEntity<CommentNumberResult>(cnr, HttpStatus.BAD_REQUEST);
		}

		commentRepo.delete(comment);
		return new ResponseEntity<CommentNumberResult>(cnr, HttpStatus.OK);
	}

}
