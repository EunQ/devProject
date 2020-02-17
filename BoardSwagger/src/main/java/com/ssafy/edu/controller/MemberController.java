package com.ssafy.edu.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.GithubMember;
import com.ssafy.edu.dto.MailUtil;
import com.ssafy.edu.dto.Member;
import com.ssafy.edu.help.MemberNumberResult;
import com.ssafy.edu.jpa.MemberRepo;
import com.ssafy.edu.response.CommonResponse;
import com.ssafy.edu.response.LoginResponse;
import com.ssafy.edu.response.SingleResult;
import com.ssafy.edu.response.UserInfoRespose;
import com.ssafy.edu.service.GithubMemberService;
import com.ssafy.edu.service.JwtTokenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
@Api(value = "MemberController", description = "회원")
@CrossOrigin("*")
public class MemberController {

	
	@PersistenceContext
	private EntityManager em;

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

//	@Autowired
//	private IMemberService service;
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private GithubMemberService githubMemberService;

	@Autowired
	private MemberRepo memberRepo;
	
	@ApiOperation(value = "회원가입", notes = "회원가입")
	@RequestMapping(value = "/addMember", method = RequestMethod.POST)
	public ResponseEntity<MemberNumberResult> addMember(@RequestBody Member dto) throws Exception {
		logger.info("---- addMember -----" + new Date());
		logger.info(dto.toString());
		
//		System.out.println(dto);
		//Member m = service.getMemberByID(dto.getEmail());
		
		Member m = memberRepo.findByEmail(dto.getEmail()).orElse(null);

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

		//service.addMember(dto);
		memberRepo.save(dto);
        memberRepo.flush();
		
		return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
	}

	@ApiOperation(value = "회원정보", notes = "회원정보")
	@RequestMapping(value = "/getMemberByID/{email}", method = RequestMethod.GET)
	public ResponseEntity<Member> getMemberByID(@PathVariable String email) throws Exception {
		System.out.println("================getMemberByID================\t" + new Date());

		//Member m = service.getMemberByID(email);
		Member m = memberRepo.findByEmail(email).orElse(null);
		
		
		if (m == null) {
			return new ResponseEntity<Member>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Member>(m, HttpStatus.OK);
	}

	@ApiOperation(value = "회원탈퇴", notes = "회원탈퇴")
	@DeleteMapping(value = "/deleteMember/{email}")
	public ResponseEntity<CommonResponse> deleteMember(@RequestHeader("x-access-token") String accesstoken, @PathVariable String email) throws Exception {
		//회원탈퇴 부분은 더 봐야됨.
		logger.info("================deleteMember================\t" + new Date());

		//Member m = service.getMemberByID(email);
		if(accesstoken == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		if(!jwtTokenService.validateToken(accesstoken) || !jwtTokenService.getUserPk(accesstoken).equals(email) ) {
			return new ResponseEntity<CommonResponse>(new CommonResponse(-1,"token isn't validate or not equals email", CommonResponse.FAIL), HttpStatus.BAD_REQUEST);
		}
		
		Member m = memberRepo.findByEmail(email).orElse(null);
		
		CommonResponse res = new CommonResponse();
		res.setCode(0);
		res.setMsg("deleteMember succ");
		res.setState(CommonResponse.SUCC);

		if (m == null) {
			res.setCode(-1);
			res.setMsg("deleteMember fail, not exist member");
			res.setState(CommonResponse.FAIL);
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}

		//service.deleteMember(email);
		memberRepo.delete(m);
        memberRepo.flush();
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@ApiOperation(value = "이메일 확인", notes = "이메일 확인")
	@RequestMapping(value = "/checkEmail/{email}", method = RequestMethod.GET)
	public ResponseEntity<MemberNumberResult> checkEmail(@PathVariable String email) throws Exception {
		System.out.println("================checkEmail================\t" + new Date());
		
		//Member m = service.getMemberByID(email);
		Member m = memberRepo.findByEmail(email).orElse(null);
		
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
		//Member m = service.getMemberByID(id);
		Member m = memberRepo.findByEmail(id).orElse(null);
		
		
		logger.info(m.toString());
		MemberNumberResult mnr = new MemberNumberResult();
		
		if(m == null || !dto.getPwd().equals(m.getPwd())) {
			mnr.setNumber(-1);
			mnr.setName("login");
			mnr.setState("fail");
			return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
		}
		
		String token = jwtTokenService.createToken(id, m.getAuth());
		
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
		
		boolean chk = jwtTokenService.validateToken(accesstoken);
		
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
		
		Member m = memberRepo.findByEmail(email).orElse(null);
		
		MemberNumberResult mnr = new MemberNumberResult();
	
		if (m == null) {
			mnr.setNumber(-1);
			mnr.setName("sendTmpPwd");
			mnr.setState("fail");
			return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
		}
		String keyCode = MailUtil.createKey();
		
		String subject = "[DEV 42.195] 비밀번호 찾기 인증코드 안내";
		String msg = "";
		msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
		msg += "<h3 style='color:blue;'>비밀번호 찾기 인증코드입니다.</h3>";
		msg += "<div style='font-size: 130%'>";
		msg += keyCode + "</strong> 를 입력해주세요.</div><br/>";
		
		MailUtil.sendMail(email, subject, msg);
		m.setPwd(keyCode);
		memberRepo.save(m);
		
		mnr.setNumber(0);
		mnr.setName("sendTmpPwd");
		mnr.setState("succ");
		
		return new ResponseEntity<MemberNumberResult>(mnr, HttpStatus.OK);
	}
	
	@ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/signin/github")
    public LoginResponse signinByProvider(@ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

		//String email  = githubMemberService.getGithubUserPrivateEmail(accessToken).getEmail();
        GithubMember githubMember = githubMemberService.getGithubUser(accessToken);
        String email = githubMember.getLogin();//github로그인시 login이 member.email(pk)가 된다.
        
		Member member = memberRepo.findByEmail(email).orElse(null);
        
        if(member == null) {
        	return new LoginResponse(1, "social login fail", "fail");
        }
//        service.changeMemberInfo(member);
        
        LoginResponse res = new LoginResponse(0, "social login success", CommonResponse.SUCC);
        res.setAccessToken(jwtTokenService.createToken(member.getEmail(), member.getAuth()));
        return res;
    }


	@ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/signup/github")
    public CommonResponse signupProvider(@ApiParam(value = "소셜 access_token가 포함된 member", required = true) @RequestBody Member member) {
    	logger.info("소셜 가입 - " + member.toString());
        GithubMember githubMember = githubMemberService.getGithubUser(member.getToken());
        //GithubUserEmail githubUserEmail = githubMemberService.getGithubUserPrivateEmail(member.getToken());
        logger.info("소설 가입 - " + githubMember.toString());// " , " + githubUserEmail.getEmail());
//      Member findMember = service.getMemberByID(githubMember.getLogin());
        
        Member findMember = memberRepo.findByEmail(githubMember.getLogin()).orElse(null);
        
        if (findMember != null)
            return new CommonResponse(1, "이미 회원가입이 되어있습니다.", CommonResponse.FAIL);
        //Member newMember = githubMemberService.getMemberByGithubMember(githubMember, githubUserEmail);
        member.setEmail(githubMember.getLogin());
        member.setGithub(githubMember.getLogin());
        
        memberRepo.save(member);
        memberRepo.flush();
        
        //service.addMember(member);
        //service.changeMemberInfo(member);
        
        return new CommonResponse(0, "social signup success", CommonResponse.SUCC);
    }
	
	@ApiOperation(value = "login_access_token으로 유저정보 알기", notes = "/api/user 로 회원정보를 알 수 있다.")
    @GetMapping(value = "/user")
    public ResponseEntity<UserInfoRespose> getUserByToken(@ApiParam(value = "loing_access_token", required = true) @RequestHeader("x-access-token") String accessToken) {
		logger.info("----getUserByToken----  " + accessToken);
    	if(accessToken == null) {
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    	if(!jwtTokenService.validateToken(accessToken)) {
    		return new ResponseEntity<>(new UserInfoRespose(1, "Token이 유효하지 않습니다.",   CommonResponse.FAIL, null),HttpStatus.BAD_REQUEST);
    	}
		String email = jwtTokenService.getUserPk(accessToken);
		logger.info("email - " + email);
        Member member = memberRepo.findByEmail(email).orElse(null);
        logger.info("member - " + member.toString());
        member.setPwd("");
        UserInfoRespose res = new UserInfoRespose(0, "UserInfo 전달", CommonResponse.SUCC, member);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
	
	@ApiOperation(value = "회원가입된 유저들", notes = "/api/users 로 회원들의 이메일을 알 수 있다.")
    @GetMapping(value = "/findAllEmail")
    public ResponseEntity<SingleResult<List<String>>> getFindAllEmail() {
		logger.info("----getFindAllEmail----  ");
		List<String> dates = new ArrayList<>();
		List<Member> users = memberRepo.findAll();
		for(Member member : users) {
			dates.add(member.getEmail());
		}
		SingleResult<List<String>> res = new SingleResult<>(0, "반한된 유저들", CommonResponse.SUCC);
		res.setData(dates);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
	
	@ApiOperation(value = "회원가입된 유저들", notes = "/api/findAllMember 로 회원들의 정보를 알 수 있다.")
    @GetMapping(value = "/findAllMember")
    public ResponseEntity<SingleResult<List<Member>>> findAllMember() {
		logger.info("----findAllMember----  ");
		List<Member> users = memberRepo.findAll();
		for(Member member : users) {
			member.setPwd("");
		}
		SingleResult<List<Member>> res = new SingleResult<>(0, "반한된 유저들", CommonResponse.SUCC);
		res.setData(users);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
	
	@ApiOperation(value = "test", notes = "/test")
    @GetMapping(value = "/test")
    public ResponseEntity<SingleResult<List<String>>> test() {
		logger.info("----tset----  ");
		TypedQuery<Member> query =  em.createQuery("select m from Member m", Member.class);
		List<String> dates = new ArrayList<>();
		List<Member> users = memberRepo.findAll();
		for(Member member : users) {
			dates.add(member.getEmail());
		}
		SingleResult<List<String>> res = new SingleResult<>(0, "반한된 유저들", CommonResponse.SUCC);
		res.setData(dates);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
	
}
