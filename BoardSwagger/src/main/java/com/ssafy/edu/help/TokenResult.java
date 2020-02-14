package com.ssafy.edu.help;

public class TokenResult {
	private String uid;
	private int role;
	private String accessToken;
	private String refreshToken;
	
	public TokenResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TokenResult(String uid, int role, String accessToken, String refreshToken) {
		super();
		this.uid = uid;
		this.role = role;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "TokenResult [uid=" + uid + ", role=" + role + ", accessToken=" + accessToken + ", refreshToken="
				+ refreshToken + "]";
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
