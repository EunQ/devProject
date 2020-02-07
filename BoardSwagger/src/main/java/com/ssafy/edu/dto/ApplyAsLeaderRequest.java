package com.ssafy.edu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyAsLeaderRequest {
	private String github_repo_name;
	private String access_token;
	private String board_id;
}
