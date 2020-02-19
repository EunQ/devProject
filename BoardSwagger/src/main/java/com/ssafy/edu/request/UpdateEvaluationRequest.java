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
public class UpdateEvaluationRequest {
	private String toMemberId;
	private int teamId;
	private int score;
	private String info;
}
