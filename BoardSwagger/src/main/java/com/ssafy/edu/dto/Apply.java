package com.ssafy.edu.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "apply")
public class Apply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apply_id")
	private int applyId;
	
	@Column(name = "team_id")
	private int teamId;
	
	@Column(name = "apply_date")
	private String applyDate;

	@Column(name = "board_id")
	private int boardId;

	@Column(name = "apply_info")
	private String applyInfo;
}
