package com.ssafy.edu.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class ApplyBoardRequest implements Serializable {
	private int teamId;
	private int boardId;
	private String info;
}
