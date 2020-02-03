package com.ssafy.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.ssafy.edu.dao.GithubMemberMapper;
import com.ssafy.edu.dto.GithubMember;

@Service
public class GithubMemberService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private GithubMemberMapper githubMemberMapper;

	/*
	private String githubClientId = "7038e018caed836c5c38";

	
	 * spring.github.ClientId = 7038e018caed836c5c38
spring.github.SecretId = 5df543f4a47e52eb209c22f9c0b1dec74a5894c5
	 
	
	private String githubSecretid = "5df543f4a47e52eb209c22f9c0b1dec74a5894c5";
	*/
	
	
	/*
	 * spring.github.url.login = https://github.com/login/oauth/authorize
		spring.github.url.token = https://github.com/login/oauth/access_token
			spring.github.url.user = https://api.github.com/user
	 */
	public com.ssafy.edu.dto.GithubMember getGithubUser(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + accessToken);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
		try {
            // Request profile
            ResponseEntity<String> response = restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), GithubMember.class);
        } catch (Exception e) {
            throw new RuntimeException("github 로그인 이상");
        }
        throw new RuntimeException("github 로그인 이상");
	}
	
	public GithubMember findGithubMemberById(long id) {
		GithubMember member = githubMemberMapper.findGithubMemberById(id);
		return member;
	}
	
	public void save(GithubMember member) {
		githubMemberMapper.save(member);
	}
}



