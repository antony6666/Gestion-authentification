package com.example.securingweb;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class userloggins {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	private String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private String UserIP;
	private String UserAction;
	private String ConnectionDate;
	
	public String getConnectionDate() {
		return ConnectionDate;
	}
	public void setConnectionDate(String connectionDate) {
		ConnectionDate = connectionDate;
	}
	public String getUserIP() {
		return UserIP;
	}
	public void setUserIP(String userIP) {
		UserIP = userIP;
	}
	public String getUserAction() {
		return UserAction;
	}
	public void setUserAction(String userAction) {
		UserAction = userAction;
	}

}
