package com.ssafy.edu.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="team_member")
public class TeamMember implements Serializable {
	private int teamMemberId;
	private String email;
	private int teamId;
	private String role;
	private int accept;
}
