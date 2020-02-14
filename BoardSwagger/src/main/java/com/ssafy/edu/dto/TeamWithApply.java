package com.ssafy.edu.dto;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Value;

@Value
public class TeamWithApply implements Serializable {
	
	private Integer teamId;
	
	private String teamDate;
	
	private String teamName;

	private String idea;
}
