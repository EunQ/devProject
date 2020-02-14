package com.ssafy.edu.dto;

public class GithubUserEmail {
	private String email;
	private boolean primary;
	private boolean verified;
	private String visibility;
	
	public GithubUserEmail() {
		super();
	}

	public GithubUserEmail(String email, boolean primary, boolean verified, String visibility) {
		super();
		this.email = email;
		this.primary = primary;
		this.verified = verified;
		this.visibility = visibility;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
}
