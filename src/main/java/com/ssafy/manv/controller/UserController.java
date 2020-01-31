package com.ssafy.manv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.manv.entity.Member;
import com.ssafy.manv.response.SingleResult;
import com.ssafy.manv.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"2. 회원 정보 관련"})
@RestController
@RequestMapping(value="/v1")
public class UserController {

	@Autowired
	private MemberService memberService;
	
	@ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@ApiOperation(value = "회원 단건 조회", notes = "회원 Member_Id로 회원을 조회한다")
	@GetMapping(value = "/user")
	public SingleResult<Member> findMember() {
	    // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String id = authentication.getName();
	    SingleResult<Member> res = new SingleResult<Member>(0, "회원 정보 조회");
	    res.setData(memberService.findByMemberId(id));
	    // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
	    return res;
	}
}
