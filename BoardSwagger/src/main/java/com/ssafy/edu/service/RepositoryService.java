package com.ssafy.edu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.edu.dto.Commit;
import com.ssafy.edu.dto.Repository;

@Service
public class RepositoryService {

	private final String oauth = "47c2d70609eccf9cc166f2e2a7b78cad1971767d"; 
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
	
	public Commit getLastCommit(String url) {
		String ownerName = getRepoOwnerByUrl(url);
		String repoName = getRepoNameByUrl(url);
		String addUrl = ownerName + "/" + repoName+"/commits";
		Commit lastCommit = new Commit();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + oauth);
		try {
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
			logger.info("getLastCommit");
			//https://api.github.com/repos/eunq/devProejct/commits
            ResponseEntity<String> response = restTemplate.exchange("https://api.github.com/repos/"+addUrl, HttpMethod.GET, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
            	lastCommit = Commit.updateFromString(response.getBody());
            	return lastCommit;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
	}
	
	public List<Commit> getLastCommit(String url, int num) {
		String ownerName = getRepoOwnerByUrl(url);
		String repoName = getRepoNameByUrl(url);
		String addUrl = ownerName + "/" + repoName+"/commits";
		List<Commit> res = new ArrayList<Commit>();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + oauth);
		try {
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
			logger.info("getLastCommit - num");
			//https://api.github.com/repos/eunq/devProejct/commits
            ResponseEntity<String> response = restTemplate.exchange("https://api.github.com/repos/"+addUrl, HttpMethod.GET, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
            	res = Commit.updateFromString(response.getBody(), num);
            	return res;
            }
        } catch (Exception e) {
        	logger.error(e.toString());
            throw null;
        }
        return null;
	}
	
	public HashMap<String, Integer> getUsersCommits(String url) {
		String ownerName = getRepoOwnerByUrl(url);
		String repoName = getRepoNameByUrl(url);
		String addUrl = ownerName + "/" + repoName+"/commits";
		logger.info("url - "  + addUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + oauth);
		HashMap<String, Integer> res = new HashMap<>();
		try {
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
			logger.info("getLastCommit - num");
			//https://api.github.com/repos/eunq/devProejct/commits
			
            ResponseEntity<String> response = restTemplate.exchange("https://api.github.com/repos/"+addUrl, HttpMethod.GET, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
        		JsonParser parser = new JsonParser();
        		JsonElement tree = parser.parse(response.getBody());
        		JsonArray list = tree.getAsJsonArray();
        		int num = list.size();
        		for(int i=0;i<num;i++) {
        			JsonElement lastObject = list.get(i);
        			JsonObject authorObject = lastObject.getAsJsonObject().get("author").getAsJsonObject();
        			String author = authorObject.get("login").getAsString();
        			if(res.get(author) != null) {
        				res.put(author, res.get(author) + 1);
        			}
        			else {
        				res.put(author, 1);
        			}
        		}
        		return res;
            }
        } catch (Exception e) {
        	logger.error(e.toString());
            return null;
        }
        return null;
	}
	

	
	
	public String getRepoNameByUrl(String url) {
		String[] stringArray = url.split("/");
		return stringArray[stringArray.length-1];
	}
	
	public String getRepoOwnerByUrl(String url) {
		String[] stringArray = url.split("/");
		return stringArray[stringArray.length-2];
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
//			Gson gson = new Gson();
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
