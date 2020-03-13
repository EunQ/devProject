package com.ssafy.edu.dto;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Apply implements Serializable {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apply_id")
	private Integer applyId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="team_id")
	@JsonManagedReference
	private Team team;
	
	@Column(name = "apply_date")
	private String applyDate;

	@Column(name = "board_id")
	private int boardId;

	@Column(name = "apply_info")
	private String applyInfo;
	
	private String idea;
}
