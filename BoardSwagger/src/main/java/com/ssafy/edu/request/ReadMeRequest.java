package com.ssafy.edu.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ReadMeRequest implements Serializable {
	private String message;
	private String content;
}
