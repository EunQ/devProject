package com.ssafy.edu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.edu.dto.Apply;
import com.ssafy.edu.dto.Apply_board;
import com.ssafy.edu.dto.Board;
import com.ssafy.edu.dto.Board_email;
import com.ssafy.edu.dto.Comment;
import com.ssafy.edu.dto.Member;
import com.ssafy.edu.dto.Post;
import com.ssafy.edu.dto.Post_board;
import com.ssafy.edu.help.BoardNumberResult;
import com.ssafy.edu.service.IBoardService;
import com.ssafy.edu.service.IMemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value = "BoardController", description = "게시글")
@CrossOrigin("*")
public class BoardController {

	public static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private IBoardService service;

	@Autowired
	private IMemberService mservice;

	@ApiOperation(value = "모든 게시판 정보를 가져온다.", response = List.class)
	@RequestMapping(value = "/getBoard", method = RequestMethod.GET)
	public ResponseEntity<List<Board>> getBoard(HttpServletRequest request) throws Exception {

		System.out.println(
				"   IP Log : " + request.getRemoteHost() + "   " + "ACTION : " + "getBoard" + "\t" + new Date());

		List<Board> list = service.getBoard();
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 추가", response = BoardNumberResult.class)
	@RequestMapping(value = "/addBoard", method = RequestMethod.POST)
	public ResponseEntity<BoardNumberResult> addBoard(@RequestParam(value = "dto_str", required = true) String dto_str,
													  @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		System.out.println("================addBoard================\t" + new Date());
		System.out.println("dto : " + dto_str);

		ObjectMapper mapper = new ObjectMapper();

		Board_email dto = mapper.readValue(dto_str, Board_email.class);
//		Board dto = mapper.readValue(dto_str, Board.class);

		String email = dto.getEmail();
//		
//		String email = "data@data";

		Member m = mservice.getMemberByID(email);
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		String now = dateformat.format(new Date());

		if (!file.isEmpty()) {
			String filename = file.getOriginalFilename();
			String filenameExtension = FilenameUtils.getExtension(filename).toLowerCase();
			File destinationFile;
			String destinationFileName;
			String fileUrl = "C:/BoardSwagger/BoardSwagger/src/main/resources/static/image/";

			SimpleDateFormat timeformat = new SimpleDateFormat("yyMMddHHmmss");
			destinationFileName = timeformat.format(new Date()) + "." + filenameExtension;
			destinationFile = new File(fileUrl + destinationFileName);

			System.out.println("File : " + destinationFileName + "======" + new Date());

			file.transferTo(destinationFile);
			String saveUrl = "http://192.168.31.122:8197/image/";
			dto.setImg(saveUrl + destinationFileName);
		} else {
			dto.setImg("none");
		}
		dto.setPeople_now(0);
		service.addBoardEmail(dto);
		int bid = service.getBoardId();

		Post post = new Post();
		post.setEmail(email);
		post.setPost_date(now);

		service.addPost(post);
		int pid = service.getBoardId();

		Post_board pb = new Post_board();

		pb.setBoard_id(bid);
		pb.setPost_id(pid);
		pb.setEmail(email);

		service.addPostBoard(pb);

		BoardNumberResult bnr = new BoardNumberResult();

		bnr.setNumber(0);
		bnr.setName("addBoard");
		bnr.setState("succ");

		return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.OK);
	}

	@ApiOperation(value = "하나의 게시글을 가져온다", response = Board.class)
	@RequestMapping(value = "/getBoardByID/{board_id}", method = RequestMethod.GET)
	public ResponseEntity<Board> getBoardByID(@PathVariable int board_id) throws Exception {
		System.out.println("================getBoardByID================\t" + new Date());

		Board b = service.getBoardByID(board_id);

		if (b == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Board>(b, HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 수정", response = BoardNumberResult.class)
	@RequestMapping(value = "/updateBoard", method = RequestMethod.PUT)
	public ResponseEntity<BoardNumberResult> updateBoard(
			@RequestParam(value = "dto_str", required = true) String dto_str,
			@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		System.out.println("================updateBoard================\t" + new Date());

		ObjectMapper mapper = new ObjectMapper();

		Board dto = mapper.readValue(dto_str, Board.class);

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		String now = dateformat.format(new Date());

		Board b = service.getBoardByID(dto.getBoard_id());
		String originImg = b.getImg();
		dto.setPeople_now(b.getPeople_now());

		if (file == null || file.isEmpty()) {
			dto.setImg(originImg);
			service.updateBoard(dto);
		} else { // 이미지 수정시
			String filename = file.getOriginalFilename();
			String filenameExtension = FilenameUtils.getExtension(filename).toLowerCase();
			File destinationFile;
			String destinationFileName;
			String fileUrl = "C:/BoardSwagger/BoardSwagger/src/main/resources/static/image/";

			SimpleDateFormat timeformat = new SimpleDateFormat("yyMMddHHmmss");
			destinationFileName = timeformat.format(new Date()) + "." + filenameExtension;
			destinationFile = new File(fileUrl + destinationFileName);

			System.out.println("File : " + destinationFileName + "======" + new Date());

			file.transferTo(destinationFile);
			String saveUrl = "http://192.168.31.122:8197/image/";
			dto.setImg(saveUrl + destinationFileName);

			service.updateBoardAll(dto);

			String originfileUrl = "C:/BoardSwagger/BoardSwagger/src/main/resources/static/image/";
			String originfilename = originImg.substring(33);

			File orignfile = new File(originfileUrl + originfilename);

			if (orignfile.exists()) {
				if (orignfile.delete())
					System.out.println("=====" + filename + "=====deleted!!");
			}

		}
		BoardNumberResult bnr = new BoardNumberResult();

		bnr.setNumber(0);
		bnr.setName("updateBoard");
		bnr.setState("succ");

		return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 삭제", notes = "board_id(int)만 담아 보내면됨(수정해야될듯?)", response = BoardNumberResult.class)
	@RequestMapping(value = "/deleteBoard", method = RequestMethod.DELETE)
	public ResponseEntity<BoardNumberResult> deleteBoard(@RequestBody Post_board dto) throws Exception {
		System.out.println("================deleteBoard================\t" + new Date());

		BoardNumberResult bnr = new BoardNumberResult();
		int board_id = dto.getBoard_id();

		Board b = service.getBoardByID(board_id);
		bnr.setName("deleteBoard");
		bnr.setNumber(board_id);

		if (b == null) {
			bnr.setState("fail");
			return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.BAD_REQUEST);
		}
		String imgurl = b.getImg();

		int pid = service.getPid(dto);
		dto.setPost_id(pid);
		service.deleteBoard(board_id);
		service.deletePostRecord(dto);

		String fileUrl = "C:/BoardSwagger/BoardSwagger/src/main/resources/static/image/";
		String filename = imgurl.substring(33);

		File file = new File(fileUrl + filename);

		if (file.exists()) {
			if (file.delete())
				System.out.println("=====" + filename + "=====deleted!!");
		}

		return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 추가", response = BoardNumberResult.class)
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public ResponseEntity<BoardNumberResult> addComment(@RequestBody Comment dto) throws Exception {
		System.out.println("================addComment================\t" + new Date());

		BoardNumberResult bnr = new BoardNumberResult();
		System.out.println(dto.toString());
		Board b = service.getBoardByID(dto.getBoard_id());
		bnr.setName("addComment");
		bnr.setNumber(dto.getBoard_id());

		if (b == null) {
			bnr.setState("fail");
			return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.BAD_REQUEST);
		}
		bnr.setState("succ");
		service.addComment(dto);

		return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 목록", response = List.class)
	@RequestMapping(value = "/getComment/{board_id}", method = RequestMethod.GET)
	public ResponseEntity<List<Comment>> getComment(@PathVariable int board_id) throws Exception {

		System.out.println("================getComment================\t" + new Date());

		List<Comment> list = service.getComment(board_id);

		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Comment>>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 삭제", response = BoardNumberResult.class)
	@RequestMapping(value = "/deleteComment/{cnum}", method = RequestMethod.DELETE)
	public ResponseEntity<BoardNumberResult> deleteComment(@PathVariable int cnum) throws Exception {
		System.out.println("================deleteComment================\t" + new Date());

		BoardNumberResult bnr = new BoardNumberResult();
		Comment c = service.getCommentByNum(cnum);
		bnr.setName("deleteComment");
		bnr.setNumber(cnum);

		if (c == null) {
			bnr.setState("fail");
			return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.BAD_REQUEST);
		}

		service.deleteComment(cnum);
		return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.OK);
	}

	
//	@ApiOperation(value = "참가하기", notes = "board_id(int), email(String)만 넣으면 됨", response = BoardNumberResult.class)
//	@RequestMapping(value = "/applyBoard", method = RequestMethod.POST)
//	public ResponseEntity<BoardNumberResult> applyBoard(@RequestBody Apply_board dto) throws Exception {
//		System.out.println("================applyBoard================\t" + new Date());
//
//		BoardNumberResult bnr = new BoardNumberResult();
//
//		int bid = dto.getBoard_id();
//
//		Board b = service.getBoardByID(bid);
//		int total_people = b.getPeople_num();
//		int now_people = b.getPeople_now();
//
//		bnr.setName("applyBoard");
//		bnr.setNumber(now_people);
//
//		if (total_people <= now_people) {
//			bnr.setState("fail");
//			return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.BAD_REQUEST);
//		}
//		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
//		String now = dateformat.format(new Date());
//
//		Apply apl = new Apply();
//		apl.setApply_date(now);
//		apl.setEmail(dto.getEmail());
//		service.addApply(apl);
//
//		int aid = service.getBoardId(); // AI된 Apply_id 값
//
//		Apply_board ab = new Apply_board();
//		ab.setApply_id(aid);
//		ab.setBoard_id(bid);
//		ab.setEmail(dto.getEmail());
//		service.addApplyBoard(ab);
//
//		now_people += 1;
//		b.setPeople_now(now_people);
//		service.updateBoard(b);
//
//		return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.OK);
//	}

	@ApiOperation(value = "게시글 검색(제목)", response = List.class)
	@RequestMapping(value = "/searchBoardByTitle/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<List<Board>> searchBoardByTitle(@PathVariable String keyword) throws Exception {

		System.out.println("================searchBoardByTitle================\t" + new Date());

		List<Board> list = service.searchBoardByTitle(keyword);

		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시글 검색(hashtag)", response = List.class)
	@RequestMapping(value = "/searchBoardByTag/{hashtag}", method = RequestMethod.GET)
	public ResponseEntity<List<Board>> searchBoardByTag(@PathVariable String hashtag) throws Exception {
		
		System.out.println("================searchBoardByTag================\t" + new Date());
		
		List<Board> list = service.searchBoardByTag(hashtag);
		
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}
}
