package com.ssafy.edu.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EvaluateTeamRequest {
	private int team_id;
	private int board_id;
	private int score;
	private String info;
}
