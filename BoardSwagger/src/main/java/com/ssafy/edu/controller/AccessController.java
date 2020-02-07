package com.ssafy.edu.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.ssafy.edu.dto.GithubAccessTokenRespose;
import com.ssafy.edu.dto.CodeRequest;
import com.ssafy.edu.dto.Member;
import com.ssafy.edu.service.GithubMemberService;
import com.ssafy.edu.service.JwtTokenService;
import com.ssafy.edu.service.MemberServiceImple;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"Github 로그인"})
@RestController
@RequestMapping(value="/api")
@CrossOrigin("*")
public class AccessController {
	

	private Logger logger = LoggerFactory.getLogger(AccessController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private GithubMemberService githubMemberSerivce;
	@Autowired
	private MemberServiceImple memberSerive;
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Value("${spring.github.ClientId}")
	private String githubClientId;
	
	@Value("${spring.github.SecretId}")
	private String githubSecretid;
	
	public Map<String, String> getQueryMap(String query)  
	{  
	    String[] params = query.split("&");  
	    Map<String, String> map = new HashMap<String, String>();  
	    for (String param : params)  
	    {  
	        String name = param.split("=")[0];  
	        if(name.length() == param.length()-1) {
	        	continue;
	        }
	        String value = param.split("=")[1];  
	        map.put(name, value);  
	    }  
	    return map;  
	}
	

	@ApiOperation(value="로그인", notes="이메일로 로그인을 하는 리턴값으로 토큰을 발행")
	@PostMapping(value = "/github/accessToken")
	public GithubAccessTokenRespose getGithubAccessToken(	@ApiParam(value = "code 번호", required = true ) @RequestBody CodeRequest codeRequest) {
		String code = codeRequest.getCode();
		logger.info("client_id - "+githubClientId);
		logger.info("client_secret - "+githubSecretid);
		logger.info(code);
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();		
		parameters.add("client_id", githubClientId);
		parameters.add("client_secret", githubSecretid);
		parameters.add("code", code);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, null);
		try {
            // Request profile
            ResponseEntity<String> response = restTemplate.postForEntity("https://github.com/login/oauth/access_token", request, String.class);
            logger.info("response");
            if (response.getStatusCode() == HttpStatus.OK) {
            	GithubAccessTokenRespose res = new GithubAccessTokenRespose();
            	logger.info(response.getBody());
            	Map<String, String> map = getQueryMap(response.getBody());
            	res.setAccess_token(map.get("access_token"));
            	res.setScope(map.get("scope"));
            	res.setToken_type(map.get("token_type"));
            	String email = githubMemberSerivce.getGithubUser(map.get("access_token")).getLogin();
            	Member member = memberSerive.getMemberByID(email);
            	if(member != null) {
            		res.setLogin_access_token(jwtTokenService.createToken(member.getEmail(), member.getAuth()));
            	}
                return res;
            }
        } catch (Exception e) {
            throw new RuntimeException("github 엑세스토큰 이상");
        }
		throw new RuntimeException("github 엑세스토큰 이상");
	}
	
	
//	@ApiOperation(value="로그인", notes="이메일로 로그인을 하는 리턴값으로 토큰을 발행")
//	@PostMapping(value = "/accessToken")
//	public AccessToken getAccessToken(	@ApiParam(value = "code 번호", required = true ) @RequestParam String code) {
//
//		logger.info(code);		
//		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
//		
//		parameters.add("client_id", githubClientId);
//		parameters.add("client_secret", githubSecretid);
//		parameters.add("code", code);
//
//
//		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, null);
//		try {
//            // Request profile
//            ResponseEntity<String> response = restTemplate.postForEntity("https://github.com/login/oauth/access_token", request, String.class);
//            logger.info("response");
//            if (response.getStatusCode() == HttpStatus.OK) {
//            	AccessToken res = new AccessToken();
//            	logger.info(response.getBody());
//            	Map<String, String> map = getQueryMap(response.getBody());
//            	res.setAccess_token(map.get("access_token"));
//            	res.setScope(map.get("scope"));
//            	res.setToken_type(map.get("token_type"));
//            	
//                return res;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("github 엑세스토큰 이상");
//        }
//		throw new RuntimeException("github 엑세스토큰 이상");
//	}
	
}
