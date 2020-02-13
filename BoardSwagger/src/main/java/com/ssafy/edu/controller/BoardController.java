package com.ssafy.edu.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.edu.dto.Board;
import com.ssafy.edu.dto.Board_email;
import com.ssafy.edu.dto.Member;
import com.ssafy.edu.dto.Post;
import com.ssafy.edu.help.BoardNumberResult;
import com.ssafy.edu.jpa.BoardRepo;
import com.ssafy.edu.jpa.MemberRepo;
import com.ssafy.edu.jpa.PostRepo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value = "BoardController", description = "게시글")
@CrossOrigin("*")
public class BoardController {

	public static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private String awsSaveFolder = "/home/ubuntu/image/";
	private String awsUrl = "http://13.209.18.235:8197/image/";
	
	@Autowired
	private BoardRepo boardRepo;

	@Autowired
	private MemberRepo memberRepo;
	
	@Autowired
	private PostRepo postRepo;

	@ApiOperation(value = "모든 게시판 정보를 가져온다.", response = List.class)
	@RequestMapping(value = "/getBoard", method = RequestMethod.GET)
	public ResponseEntity<List<Board>> getBoard(HttpServletRequest request) throws Exception {

		System.out.println(
				"   IP Log : " + request.getRemoteHost() + "   " + "ACTION : " + "getBoard" + "\t" + new Date());

		List<Board> boardList = boardRepo.findAll();
		if (boardList.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(boardList, HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 추가", response = BoardNumberResult.class)
	@RequestMapping(value = "/addBoard", method = RequestMethod.POST)
	public ResponseEntity<BoardNumberResult> addBoard(@RequestParam(value = "boardString", required = true) String boardString,
													  @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		System.out.println("================addBoard================\t" + new Date());

		ObjectMapper mapper = new ObjectMapper();

		Board_email dto = mapper.readValue(boardString, Board_email.class);
		
		Board board = new Board();
		board.setApplyEnd(dto.getApplyEnd());
		board.setApplyStart(dto.getApplyStart());
		board.setEnd(dto.getEnd());
		board.setStart(dto.getStart());
		board.setHost(dto.getHost());
		board.setInfo(dto.getInfo());
		board.setLocation(dto.getLocation());
		board.setPeopleNum(dto.getPeopleNum());
		board.setPrice(dto.getPrice());
		board.setTitle(dto.getTitle());
		
		String email = dto.getEmail();

		Member m = memberRepo.findByEmail(email).orElse(null);
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		String now = dateformat.format(new Date());

		if (!file.isEmpty()) {
			String filename = file.getOriginalFilename();
			String filenameExtension = FilenameUtils.getExtension(filename).toLowerCase();
			File destinationFile;
			String destinationFileName;
			//String fileUrl = "C:/BoardSwagger/BoardSwagger/src/main/resources/static/image/";
			String fileUrl = awsSaveFolder;
			
			SimpleDateFormat timeformat = new SimpleDateFormat("yyMMddHHmmss");
			destinationFileName = timeformat.format(new Date()) + "." + filenameExtension;
			destinationFile = new File(fileUrl + destinationFileName);

			System.out.println("File : " + destinationFileName + "======" + new Date());

			file.transferTo(destinationFile);
			//String saveUrl = "http://192.168.31.122:8197/image/";
			String saveUrl = awsUrl;
			board.setImg(saveUrl + destinationFileName);
		} else {
			board.setImg("none");
		}
		
		board.setPeopleNow(0);
		boardRepo.save(board);
		
		int boardId = boardRepo.getBoardId();

		Post post = new Post();
		post.setEmail(email);
		post.setPostDate(now);
		post.setBoardId(boardId);

		postRepo.save(post);
		postRepo.flush();

		BoardNumberResult bnr = new BoardNumberResult();

		bnr.setNumber(0);
		bnr.setName("addBoard");
		bnr.setState("succ");

		return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.OK);
	}

	@ApiOperation(value = "하나의 게시글을 가져온다", response = Board.class)
	@RequestMapping(value = "/getBoardByID/{boardId}", method = RequestMethod.GET)
	public ResponseEntity<Board> getBoardByID(@PathVariable int boardId) throws Exception {
		System.out.println("================getBoardByID================\t" + new Date());

		Board board = boardRepo.findById(boardId).orElse(null);

		if (board == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 수정", response = BoardNumberResult.class)
	@RequestMapping(value = "/updateBoard", method = RequestMethod.PUT)
	public ResponseEntity<BoardNumberResult> updateBoard(
			@RequestParam(value = "boardString", required = true) String boardString,
			@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		System.out.println("================updateBoard================\t" + new Date());

		ObjectMapper mapper = new ObjectMapper();

		Board boardReq = mapper.readValue(boardString, Board.class);

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		String now = dateformat.format(new Date());

		Board boardInDb = boardRepo.findById(boardReq.getBoardId()).orElse(null);
		
		String originImg = boardInDb.getImg();
		boardReq.setPeopleNow(boardInDb.getPeopleNow());

		if (file == null || file.isEmpty()) {
			boardReq.setImg(originImg);
			boardRepo.save(boardReq);
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
			boardReq.setImg(saveUrl + destinationFileName);

			boardRepo.save(boardReq);
			boardRepo.flush();

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

	@ApiOperation(value = "게시글 삭제", notes = "게시글 삭제", response = BoardNumberResult.class)
	@RequestMapping(value = "/deleteBoard/{boardId}", method = RequestMethod.DELETE)
	public ResponseEntity<BoardNumberResult> deleteBoard(@PathVariable int boardId) throws Exception {
		System.out.println("================deleteBoard================\t" + new Date());
		System.out.println("boardId : " + boardId );
		BoardNumberResult bnr = new BoardNumberResult();

		Board boardInDb = boardRepo.findOneByBoardId(boardId).orElse(null);
		bnr.setName("deleteBoard");
		bnr.setNumber(boardId);

		if (boardInDb == null) {
			bnr.setState("fail");
			return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.BAD_REQUEST);
		}
		String imgurl = boardInDb.getImg();

		Post delPost = postRepo.findOneByBoardId(boardId);
		postRepo.delete(delPost);
		boardRepo.delete(boardInDb);
		bnr.setState("succ");

		String fileUrl = "C:/BoardSwagger/BoardSwagger/src/main/resources/static/image/";
		String filename = imgurl.substring(33);

		File file = new File(fileUrl + filename);

		if (file.exists()) {
			if (file.delete())
				System.out.println("=====" + filename + "=====deleted!!");
		}

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
//		int total_people = b.getPeopleNum();
//		int now_people = b.getPeopleNow();
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
//		b.setPeopleNow(now_people);
//		service.updateBoard(b);
//
//		return new ResponseEntity<BoardNumberResult>(bnr, HttpStatus.OK);
//	}

	@ApiOperation(value = "게시글 검색(제목)", response = List.class)
	@RequestMapping(value = "/searchBoardByTitle/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<List<Board>> searchBoardByTitle(@PathVariable String keyword) throws Exception {

		System.out.println("================searchBoardByTitle================\t" + new Date());

		List<Board> searchList = boardRepo.findByKeyword(keyword);

		if (searchList.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(searchList, HttpStatus.OK);
	}
}
