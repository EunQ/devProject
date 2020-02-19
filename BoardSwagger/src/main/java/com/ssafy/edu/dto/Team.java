package com.ssafy.edu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "team")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Team implements Serializable {

	static final public String STATE_READY = "READY";
	static final public String STATE_RUN = "RUN"; //apply시간 이후 진행되는
	static final public String STATE_END = "END"; //임의로 설정한 종료상태
	static final public String STATE_END_HALF = "END_HALF"; //1차에서 떨어진 사람
	static final public String STATE_END_FULL = "END_FULL"; //2차 결과 종료.
	//Half , full 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="team_id")
	private Integer teamId;
	
	@Column(name="team_date")
	private String teamDate;
	
	@Column(name="team_state")
	@ColumnDefault("RUN")
	private String teamState; //Run, End 지금은 쓰지 않음.
	
	@Column(name="team_member_num")
	private int teamMemberNum;
	
	@Column(name="github_repo_url")
	private String githubRepoUrl;
	
	@Column(name="team_name")
	private String teamName;
	

	@OneToMany(mappedBy = "team",cascade = CascadeType.ALL)
	@JsonBackReference
	@ToString.Exclude
    private List<TeamMember> teamMembers = new ArrayList<>();
	

	@OneToMany(mappedBy = "team",cascade = CascadeType.ALL)
	@JsonBackReference
	@ToString.Exclude
    private List<Apply> applys = new ArrayList<>();
	
	public void subMember() {
		teamMemberNum--;
	}
	
	public void addMember() {
		teamMemberNum++;
	}
	
	
}
