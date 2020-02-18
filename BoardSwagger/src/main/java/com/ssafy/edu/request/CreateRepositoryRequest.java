package com.ssafy.edu.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class CreateRepositoryRequest implements Serializable {
	private int teamId;
	private String repoName;
}
