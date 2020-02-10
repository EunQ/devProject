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

@Entity
@Table(name="team_member")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TeamMember implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="team_member_id")
	private int teamMemberId;
	
	@Column(name ="email")
	private String email;
	
	@Column(name="team_id")
	private int teamId;
	
	@Column(name = "role")
	private String role;
	
	@Column(name="accept")
	private int accept;
}
