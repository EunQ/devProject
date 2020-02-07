package com.ssafy.edu.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Repository implements Serializable {
	private Long id;
	private String name;
	private GithubMember owner;
	private String html_url;
	
	@SerializedName ("private")
	private boolean rprivate;
}
