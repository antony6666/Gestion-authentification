package com.example.securingweb;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Connexion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String username;
	private int nbConnexion;
	private String mdp1;
	private String mdp2;
	private String mdp3;
	
	public String getMdp1() {
		return mdp1;
	}
	public void setMdp1(String mdp1) {
		this.mdp1 = mdp1;
	}
	public String getMdp2() {
		return mdp2;
	}
	public void setMdp2(String mdp2) {
		this.mdp2 = mdp2;
	}
	public String getMdp3() {
		return mdp3;
	}
	public void setMdp3(String mdp3) {
		this.mdp3 = mdp3;
	}
	
	public int getNbConnexion() {
		return nbConnexion;
	}
	public void setNbConnexion(int nbConnexion) {
		this.nbConnexion = nbConnexion;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
}


	