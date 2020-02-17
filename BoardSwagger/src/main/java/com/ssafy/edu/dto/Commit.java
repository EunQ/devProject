package com.ssafy.edu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
@NoArgsConstructor
public class Commit implements Serializable {

	private String sha;
	private String author;
	private String date;
	
	static public Commit updateFromString(String response) {
		Commit commit = new Commit();
		JsonParser parser = new JsonParser();
		JsonElement tree = parser.parse(response);
		if(!tree.isJsonArray()) {
			System.out.println("array fail");
//			return null;
		}
		JsonArray list = tree.getAsJsonArray();
		JsonElement lastObject = list.get(0);
		String sha = lastObject.getAsJsonObject().get("sha").getAsString();
		if(sha == null) {
			return commit;
		}
		commit.setSha(sha);
		JsonObject commitObject = lastObject.getAsJsonObject().get("commit").getAsJsonObject();
		JsonObject authorObject = lastObject.getAsJsonObject().get("author").getAsJsonObject();
		String author = authorObject.get("login").getAsString();
		commit.setAuthor(author);
		String date = commitObject.get("author").getAsJsonObject().get("date").getAsString();
		commit.setDate(date);
		return commit;
	}
	
	static public List<Commit> updateFromString(String response, int num) {
		List<Commit> commits = new ArrayList<Commit>();
		JsonParser parser = new JsonParser();
		JsonElement tree = parser.parse(response);
		if(!tree.isJsonArray()) {
			System.out.println("array fail");
//			return null;
		}
		JsonArray list = tree.getAsJsonArray();
		num = Math.min(num, list.size());
		for(int i=0;i<num;i++) {
			JsonElement lastObject = list.get(i);
			Commit commit = new Commit();
			String sha = lastObject.getAsJsonObject().get("sha").getAsString();
			if(sha == null) {
				return commits;
			}
			commit.setSha(sha);
			JsonObject commitObject = lastObject.getAsJsonObject().get("commit").getAsJsonObject();
			JsonObject authorObject = lastObject.getAsJsonObject().get("author").getAsJsonObject();
			String author = authorObject.get("login").getAsString();
			commit.setAuthor(author);
			String date = commitObject.get("author").getAsJsonObject().get("date").getAsString();
			commit.setDate(date);
			commits.add(commit);
		}
		return commits;
	}
	
}
