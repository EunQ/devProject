package com.ssafy.edu.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@AllArgsConstructor
@Setter
@Getter
public class CreateTeamRequest implements Serializable {
	private String teamName;
	private String[] memberList;
	private int boardId;
	private String info;
	private String idea;
}
