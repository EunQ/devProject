package com.ssafy.edu.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.Notice;
import com.ssafy.edu.help.NoticeNumberResult;
import com.ssafy.edu.jpa.NoticeRepo;
import com.ssafy.edu.service.INoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value = "NoticeController", description = "공지")
@CrossOrigin("*")
public class NoticeController {
	
//	@Autowired
//	INoticeService nservice;
	
	@Autowired
	NoticeRepo noticeRepo;
	
	@ApiOperation(value = "모든 공지 정보를 가져온다.", response = List.class)
	@RequestMapping(value = "/getNotice", method = RequestMethod.GET)
	public ResponseEntity<List<Notice>> getBoard(HttpServletRequest request) throws Exception {

		System.out.println(
				"   IP Log : " + request.getRemoteHost() + "   " + "ACTION : " + "getBoard" + "\t" + new Date());
		List<Notice> noticeList = noticeRepo.findAll();
		
		return new ResponseEntity<List<Notice>>(noticeList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지 추가", notes = "공지 추가")
	@RequestMapping(value = "/addNotice", method = RequestMethod.POST)
	public ResponseEntity<NoticeNumberResult> addNotice(@RequestBody Notice notice) throws Exception {
		System.out.println("================addNotice================\t" + new Date());
		
//		Notice n = nservice.getNoticeByID(notice.getNoticeId());
		Notice n = noticeRepo.findOneByNoticeId(notice.getNoticeId());
		
		NoticeNumberResult nnr = new NoticeNumberResult();
		nnr.setNumber(0);
		nnr.setName("addNotice");

		if (n != null) {
			nnr.setNumber(-1);
			nnr.setState("fail");
			return new ResponseEntity<NoticeNumberResult>(nnr, HttpStatus.OK);
		}
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String now = dateformat.format(new Date());
		
		nnr.setState("succ");
		
		notice.setDate(now);
		noticeRepo.save(notice);
		return new ResponseEntity<NoticeNumberResult>(nnr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "하나의 공지를 가져온다", response = Notice.class)
	@RequestMapping(value = "/getNoticeByID/{noticeId}", method = RequestMethod.GET)
	public ResponseEntity<Notice> getNoticeByID(@PathVariable int noticeId) throws Exception {
		System.out.println("================getNoticeByID================\t" + new Date());

		Notice notice = noticeRepo.findOneByNoticeId(noticeId);

		if (notice == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Notice>(notice, HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지 삭제", notes = "공지 삭제")
	@RequestMapping(value = "/deleteNotice/{noticeId}", method = RequestMethod.DELETE)
	public ResponseEntity<NoticeNumberResult> deleteNotice(@PathVariable int noticeId) throws Exception {
		System.out.println("================deleteMember================\t" + new Date());

		Notice notice = noticeRepo.findOneByNoticeId(noticeId);

		NoticeNumberResult nnr = new NoticeNumberResult();
		nnr.setNumber(0);
		nnr.setName("deleteMember");

		if (notice == null) {
			nnr.setNumber(-1);
			nnr.setState("fail");
			return new ResponseEntity<NoticeNumberResult>(nnr, HttpStatus.OK);
		}

		noticeRepo.delete(notice);
		nnr.setState("succ");
		return new ResponseEntity<NoticeNumberResult>(nnr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지 수정", notes = "공지 수정")
	@RequestMapping(value = "/updateNotice", method = RequestMethod.POST)
	public ResponseEntity<NoticeNumberResult> updateNotice(@RequestBody Notice notice) throws Exception {
		System.out.println("================updateNotice================\t" + new Date());
		
		Notice n = noticeRepo.findOneByNoticeId(notice.getNoticeId());
		NoticeNumberResult nnr = new NoticeNumberResult();
		nnr.setNumber(0);
		nnr.setName("updateNotice");

		if (n == null) {
			nnr.setNumber(-1);
			nnr.setState("fail");
			return new ResponseEntity<NoticeNumberResult>(nnr, HttpStatus.OK);
		}
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String now = dateformat.format(new Date());
		
		notice.setDate(now);
		noticeRepo.save(notice);
		
		nnr.setState("succ");
		return new ResponseEntity<NoticeNumberResult>(nnr, HttpStatus.OK);
	}
}
