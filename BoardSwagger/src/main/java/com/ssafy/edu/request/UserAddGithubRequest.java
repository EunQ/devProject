package com.ssafy.edu.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserAddGithubRequest implements Serializable {
	private String backEndToken;
	private String githubToken;
}
