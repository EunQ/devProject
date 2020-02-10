package com.ssafy.edu.service;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.ssafy.edu.dto.Member;
import com.ssafy.edu.dto.Repository;

import springfox.documentation.spring.web.json.Json;

@Service
public class RepositoryService {

	public static final Logger logger = LoggerFactory.getLogger(RepositoryService.class);

	@Autowired
	private RestTemplate restTemplate;
	
	public Repository createRepository(String name,  String githubAccessToken) {
		Repository repository = new Repository();
		repository.setName(name);
		repository.setRprivate(false);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + githubAccessToken);
		Gson gson = new Gson();
		HttpEntity<String> request = new HttpEntity<>(gson.toJson(repository).toString(), headers);
		logger.info(name +" , " + githubAccessToken);
		try {
            // Request profile
			logger.info(gson.toJson(repository).toString());
			logger.info(request.toString());
            ResponseEntity<String> response = restTemplate.postForEntity("https://api.github.com/user/repos", request, String.class);
            logger.info("response");
            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                return gson.fromJson(response.getBody(), Repository.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("createRepository 이상");
        }
		return null;
	}
	
	public boolean checkRepositoryName(String name,String gitHubLogin, String githubAccessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + githubAccessToken);
		try {
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
			logger.info(name+ ", " + gitHubLogin +", "+ githubAccessToken);
            ResponseEntity<String> response = restTemplate.exchange("https://api.github.com/user/repos", HttpMethod.GET, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
            	Gson gson = new Gson();
            	Repository[] repositories = gson.fromJson(response.getBody(), Repository[].class);
            	for(Repository respo : repositories) {
            		logger.info(respo.getName() + " = " + name + " , " + respo.getName().equals(name));
            		if(respo.getName().equals(name)) {
            			logger.info("return false");
            			return false;
            		}
            	}
            	return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("github/repo 이상");
        }
        throw new RuntimeException("github/repo 이상");
	}
	public String getRepoNameByUrl(String url) {
		String[] stringArray = url.split("/");
		return stringArray[stringArray.length-1];
	}
	
	public boolean addTeamMember(String githubAccessToken,String ownerName,String repoUrl ,String memberGithub) {
		//PUT 
		logger.info(githubAccessToken);
		logger.info(ownerName);
		logger.info(repoUrl);
		logger.info(memberGithub);
		String repoName = getRepoNameByUrl(repoUrl);
		logger.info("to put - "+ "https://api.github.com/repos/"+ownerName+"/"+repoName+"/collaborators/"+memberGithub);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + githubAccessToken);
		try {
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
			Gson gson = new Gson();
			///repos/:owner/:repo/collaborators/:username
            ResponseEntity<String> response = restTemplate.exchange("https://api.github.com/repos/"+ownerName+"/"+repoName+"/collaborators/"+memberGithub, HttpMethod.PUT, request, String.class);
            if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() ==HttpStatus.OK ) {
            	logger.info("add github collaborators");
            	return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("service addTeamMember 이상");
        }
        return false;
	}
}