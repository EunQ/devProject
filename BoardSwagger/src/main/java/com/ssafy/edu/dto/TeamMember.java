package com.ssafy.edu.dto;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="team_member")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(exclude = "team")
public class TeamMember implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="team_member_id")
	private Integer teamMemberId;
	
	@Column(name ="email")
	private String email;
	
	@ManyToOne
	@JoinColumn(name="team_id")
	@JsonManagedReference
	private Team team;
	
	@Column(name = "role")
	private String role;
	
	@Column(name="accept")
	private int accept;
	
	final static public int  ACCEPT_STATE_READY = 0;
	final static public int  ACCEPT_STATE_DONE  = 1;
	

	final static public String ROLE_LEADER = "LEADER";
	final static public String ROLE_MEMBER = "MEMBER";
	
	public boolean isLeader() {
		if(role.equals(ROLE_LEADER)) {
			return true;
		}
		return false;
	}
}
