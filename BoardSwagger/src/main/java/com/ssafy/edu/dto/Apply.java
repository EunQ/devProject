package com.ssafy.edu.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "apply")
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Apply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apply_id")
	private Integer applyId;
	
	@Column(name = "team_id")
	private int teamId;
	
	@Column(name = "apply_date")
	private String applyDate;

	@Column(name = "board_id")
	private int boardId;

	@Column(name = "apply_info")
	private String applyInfo;
	
	private String idea;
}
