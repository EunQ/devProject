package com.ssafy.manv.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.manv.entity.GithubMember;
import com.ssafy.manv.entity.Member;
import com.ssafy.manv.response.LoginResponse;
import com.ssafy.manv.response.SingleResult;
import com.ssafy.manv.security.config.JwtTokenProvider;
import com.ssafy.manv.service.GithubMemberService;
import com.ssafy.manv.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"1. 가입 로그인"})
@RestController
@RequestMapping(value="/v1")
public class SignController {
	
	//@Autowired
	//private MemberJpaRepo memberJpaRepo;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private GithubMemberService githubMemberService;
	
	private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	private Logger logger = LoggerFactory.getLogger(SignController.class);

	
	
	@ApiOperation(value="로그인", notes="이메일로 로그인을 하는 리턴값으로 토큰을 발행")
	@PostMapping(value = "/login")
	@CrossOrigin("*")
	public LoginResponse login(	@ApiParam(value = "회원 아이디", required = true ) @RequestParam String id,
								@ApiParam(value = "비밀번호", required = true) @RequestParam String pwd) {

		logger.info(id + " " + pwd);
		Member member = memberService.login(id, pwd);
		logger.info("login - " + member);
		
		LoginResponse res = null;
		if(member != null) {
			res = new LoginResponse(0, "login success");
			res.setAccessToken(jwtTokenProvider.createToken(member.getUsername(), member.getAuth()));
		}
		else {
			logger.info("login fail");
			res = new LoginResponse(1, "login fail");
		}
		
		return res;
	}

	@ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/signin/github")
    public LoginResponse signinByProvider(@ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {

        GithubMember githubUser = githubMemberService.getGithubUser(accessToken);
        Member member = memberService.findByMemberId(githubUser.getId()+"|GITHUB");
        //Member member = null;// memberJpaRepo.findByMemberId(githubUser.getId() +"|GITHUB").orElseThrow(RuntimeException::new);
        if(member == null) {
        	return new LoginResponse(1, "social login failed, don't exist in DB");
        }
        LoginResponse res = new LoginResponse(0, "social login success");
        res.setAccessToken(jwtTokenProvider.createToken(member.getUsername(), member.getAuth()));
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
