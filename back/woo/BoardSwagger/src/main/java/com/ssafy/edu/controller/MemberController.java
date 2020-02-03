package com.ssafy.edu.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.config.JwtTokenProvider;
import com.ssafy.edu.dao.GithubMemberMapper;
import com.ssafy.edu.dto.GithubMember;
import com.ssafy.edu.dto.MailUtil;
import com.ssafy.edu.dto.Member;
import com.ssafy.edu.help.MemberNumberResult;
import com.ssafy.edu.service.IJwtService;
import com.ssafy.edu.service.IMemberService;
import com.ssafy.response.LoginResponse;
import com.ssafy.response.SingleResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
@Api(value = "MemberController", description = "회원")
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private IMemberService service;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private com.ssafy.edu.service.GithubMemberService githubMemberService;

	@ApiOperation(value = "회원가입", notes = "회원가입")
	@RequestMapping(value = "/addMember", method = RequestMethod.POST)
	public ResponseEntity<MemberNumberResult> addMember(@RequestBody Member dto) throws Exception {
		System.out.println("================addMember================\t" + new Date());
		System.out.println(dto.toString());
		
//		System.out.println(dto);
		Member m = service.getMemberByID(dto.getEmail());

		MemberNumberResult mnr = new MemberNumberResult();
		mnr.setNumber(0);
		mnr.setName("addMember");
		mnr.setState("succ");

		if (m != null) {
			mnr.setNumber(-1);
			mnr.setName("addMember");
			mnr.setState("fail");
			return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
		}

		service.addMember(dto);
		return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
	}

	@ApiOperation(value = "회원정보", notes = "회원정보")
	@RequestMapping(value = "/getMemberByID/{email}", method = RequestMethod.GET)
	public ResponseEntity<Member> getMemberByID(@PathVariable String email) throws Exception {
		System.out.println("================getMemberByID================\t" + new Date());

		Member m = service.getMemberByID(email);

		if (m == null) {
			return new ResponseEntity<Member>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Member>(m, HttpStatus.OK);
	}

	@ApiOperation(value = "회원탈퇴", notes = "회원탈퇴")
	@RequestMapping(value = "/deleteMember/{email}", method = RequestMethod.GET)
	public ResponseEntity<MemberNumberResult> deleteMember(@PathVariable String email) throws Exception {
		System.out.println("================deleteMember================\t" + new Date());

		Member m = service.getMemberByID(email);

		MemberNumberResult mnr = new MemberNumberResult();
		mnr.setNumber(0);
		mnr.setName("deleteMember");
		mnr.setState("succ");

		if (m == null) {
			mnr.setNumber(-1);
			mnr.setName("deleteMember");
			mnr.setState("fail");
			return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
		}

		service.deleteMember(email);
		return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
	}

	@ApiOperation(value = "이메일 확인", notes = "이메일 확인")
	@RequestMapping(value = "/checkEmail/{email}", method = RequestMethod.GET)
	public ResponseEntity<MemberNumberResult> checkEmail(@PathVariable String email) throws Exception {
		System.out.println("================checkEmail================\t" + new Date());
		
		Member m = service.getMemberByID(email);
		
		MemberNumberResult mnr = new MemberNumberResult();
		mnr.setNumber(0);
		mnr.setName("checkEmail");
		mnr.setState("succ");

		if (m != null) {
			mnr.setNumber(-1);
			mnr.setName("checkEmail");
			mnr.setState("fail");
			return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
		}

		return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "로그인", notes = "로그인")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<MemberNumberResult> login(@RequestBody Member dto) throws Exception {
		System.out.println("================login================\t" + new Date());
		logger.info(dto.toString());
		
		String id = dto.getEmail();
		Member m = service.getMemberByID(id);
		
		logger.info(m.toString());
		MemberNumberResult mnr = new MemberNumberResult();
		
		if(m == null || !dto.getPwd().equals(m.getPwd())) {
			mnr.setNumber(-1);
			mnr.setName("login");
			mnr.setState("fail");
			return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
		}
		
		String token = jwtTokenProvider.createToken(id, m.getRole());
		System.out.println(token + "======" + new Date());

		mnr.setNumber(0);
		mnr.setName(token);
		mnr.setState("succ");

		return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "로그인중 확인", notes = "로그인중 확인")
	@RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
	public ResponseEntity<MemberNumberResult> checkLogin(@RequestHeader("x-access-token") String accesstoken) throws Exception {
		System.out.println("================checkLogin================\t" + new Date());
		System.out.println("token : " + accesstoken);
		MemberNumberResult mnr = new MemberNumberResult();
		
		boolean chk = jwtTokenProvider.validateToken(accesstoken);
		
		if(!chk) {
			mnr.setNumber(-1);
			mnr.setName("checkLogin");
			mnr.setState("fail");
			return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
		}
		mnr.setNumber(0);
		mnr.setName("checkLogin");
		mnr.setState("succ");

		return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "임시비밀번호 발송", notes = "등록된 이메일로 임시 비밀번호 발급")
	@RequestMapping(value = "/sendTmpPwd/{email}", method = RequestMethod.GET)
	public ResponseEntity<MemberNumberResult> sendTmpPwd(@PathVariable String email) throws Exception {
		System.out.println("================sendTmpPwd================\t" + new Date());
		
		Member m = service.getMemberByID(email);
		
		MemberNumberResult mnr = new MemberNumberResult();
	
		if (m == null) {
			mnr.setNumber(-1);
			mnr.setName("sendTmpPwd");
			mnr.setState("fail");
			return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
		}
		String keyCode = MailUtil.createKey();
		
		String subject = "[HELLO!] 비밀번호 찾기 인증코드 안내";
		String msg = "";
		msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
		msg += "<h3 style='color:blue;'>비밀번호 찾기 인증코드입니다.</h3>";
		msg += "<div style='font-size: 130%'>";
		msg += keyCode + "</strong> 를 입력해주세요.</div><br/>";
		
		MailUtil.sendMail(email, subject, msg);
		m.setPwd(keyCode);
		service.changePwd(m);
		
		mnr.setNumber(0);
		mnr.setName("sendTmpPwd");
		mnr.setState("succ");
		
		return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/signin/github")
    public LoginResponse signinByProvider(@ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

        GithubMember githubUser = githubMemberService.getGithubUser(accessToken);
        GithubMember member = githubMemberService.findGithubMemberById(githubUser.getId());
        if(member == null) {
        	return new LoginResponse(1, "social login fail");
        }
        LoginResponse res = new LoginResponse(0, "social login success");
        res.setAccessToken(jwtTokenProvider.createToken(String.valueOf(githubUser.getId()), "USER"));
        return res;
    }

    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/signup/github")
    public SingleResult<GithubMember> signupProvider(@ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

    	logger.info("소셜 가입 - " + accessToken);
        GithubMember githubMember = githubMemberService.getGithubUser(accessToken);
        logger.info("소설 가입 - " + githubMember.toString());
        GithubMember member = githubMemberService.findGithubMemberById(githubMember.getId());
        if (member != null)
            return new SingleResult<GithubMember>(1, "이미 회원가입이 되어있습니다.");

        githubMemberService.save(githubMember);
        SingleResult<GithubMember> res =  new SingleResult<>(0, "social signup success");
        res.setData(githubMember);
        return res;
    }
	
}
