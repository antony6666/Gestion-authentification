package com.example.securingweb;

public class UserProperties {
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getNbconnect() {
		return nbconnect;
	}
	public void setNbconnect(int nbconnect) {
		this.nbconnect = nbconnect;
	}
	private String newpassword;
	private String oldpassword;
	private String username;
	private int nbconnect;
	
}
