package com.ssafy.edu.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddTeamMemberRequest {
	private String accessToken;
	private String memberEmail;
	private int teamId;
}
