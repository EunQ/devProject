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
import com.ssafy.edu.dto.GithubUserEmail;
import com.ssafy.edu.dto.Member;

@Service
public class GithubMemberService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Gson gson;
	
	
//	public GithubUserEmail[] getGithubUserEmail(String accessToken) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		headers.set("Authorization", "Bearer " + accessToken);
//		
//		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
//		try {
//            // Request profile
//            ResponseEntity<String> response = restTemplate.exchange("https://api.github.com/user/emails", HttpMethod.GET, request, String.class);
//            if (response.getStatusCode() == HttpStatus.OK) {
//                return gson.fromJson(response.getBody(), GithubUserEmail[].class);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("github 로그인 이상");
//        }
//        throw new RuntimeException("github 로그인 이상");
//	}
	
//	public GithubUserEmail getGithubUserPrivateEmail(String accessToken) {
//		GithubUserEmail[] emails = getGithubUserEmail(accessToken);
//		for(GithubUserEmail email : emails) {
//			if(email.isPrimary()) {
//				return email;
//			}
//		}
//		return null;
//	}
//	
	public Member getMemberByGithubMember(GithubMember githubMember, GithubUserEmail githubUserEmail) {
		Member member = new Member();
		member.setEmail(githubUserEmail.getEmail());
		member.setName(githubMember.getName());
		member.setAuth("USER");//0은 일반 사용자
		member.setPwd(null);
		member.setGithub(githubMember.getLogin());
		return member;
	}
	
	public GithubMember getGithubUser(String accessToken) {
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
	
}



