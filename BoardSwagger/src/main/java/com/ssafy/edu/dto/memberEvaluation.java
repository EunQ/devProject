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
@Table(name="member_evaluation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class memberEvaluation implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="member_evaluation_id")
	private int memberEvaluationId;
	
	@Column(name="to_member")
	private String toMember;
	
	@Column(name="from_member")
 	private String fromMember;
	
	private int score;
	
	private String info;
	
	@Column(name="e_date")
	private String eDate;
	
	@Column(name="team_id")
	private int teamId;
}
