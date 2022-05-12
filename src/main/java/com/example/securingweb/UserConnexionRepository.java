package com.example.securingweb;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserConnexionRepository  extends CrudRepository<Connexion, Object> {
	Connexion findByUsername(String username);
}
