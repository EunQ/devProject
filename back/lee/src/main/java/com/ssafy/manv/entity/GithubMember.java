package com.ssafy.manv.entity;

import java.io.Serializable;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class GithubMember implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private String login;
	private String node_id;
	private String avatar_url;
	private String url;
	private String html_url;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNode_id() {
		return node_id;
	}
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHtml_url() {
		return html_url;
	}
	public void setHtml_url(String html_url) {
		this.html_url = html_url;
	}
	@Override
	public String toString() {
		return "GithubMember [id=" + id + ", login=" + login + ", node_id=" + node_id + ", avatar_url=" + avatar_url
				+ ", url=" + url + ", html_url=" + html_url + "]";
	}
	
	
	
	
}
