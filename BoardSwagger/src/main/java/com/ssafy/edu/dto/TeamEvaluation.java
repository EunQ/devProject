package com.ssafy.edu.dto;

import java.io.Serializable;

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
@Table(name="team_evaluation")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamEvaluation implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="team_evaluation_id")
	private int teamEvaluationId;
	
	private int score;
	
	private String info;
	
	@Column(name="e_date")
	private String eDate;
	
	@Column(name="team_id")
	private int teamId;
	
	private String host;
}
