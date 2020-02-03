package com.ssafy.manv.service;

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
import com.ssafy.manv.dao.GithubMemberMapper;
import com.ssafy.manv.entity.GithubMember;
import com.ssafy.manv.entity.MemberAndGithub;

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

	@Value("${spring.github.ClientId}")
	private String githubClientId;

	@Value("${spring.github.SecretId}")
	private String githubSecretid;
	
	public GithubMember getGithubUser(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + accessToken);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
		try {
            // Request profile
            ResponseEntity<String> response = restTemplate.exchange(env.getProperty("spring.github.url.user"), HttpMethod.GET, request, String.class);
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
	
	public void saveConnect(MemberAndGithub mag) {
		githubMemberMapper.saveConnect(mag);
	}
}



