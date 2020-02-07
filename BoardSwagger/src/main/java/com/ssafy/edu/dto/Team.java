package com.ssafy.edu.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "team")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Team implements Serializable {

	static final public String STATE_RUN = "RUN";
	static final public String STATE_END = "END";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="team_id")
	private int teamId;
	
	@Column(name="team_date")
	private String teamDate;
	
	@Column(name="team_state")
	@ColumnDefault("RUN")
	private String teamState; //Run, End
	
	@Column(name ="team_leader")
	private String teamLeader;
	
	@Column(name="team_member_num")
	private int teamMemberNum;
	
	@Column(name="board_id")
	private String boardId;
	
	@Column(name="github_repo_url")
	private String githubRepoUrl;
}
