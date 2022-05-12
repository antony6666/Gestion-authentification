package com.example.securingweb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebAppController {
	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserLogginsRepository userLogginsRepository;
	
	@Autowired
	UserConnexionRepository userConnexionRepository;
	
	

	/**
	 * Méthode pour récupérer la page de création d'un nouveau compte
	 * @param model
	 * @return envoyer vers la nouvelle page de création
	 */
	
	@GetMapping("/newaccount")
	public String userForm(Model model) {
		model.addAttribute("user", new UsernamePassword());
		return "newaccount";
	}

	@PostMapping("/newaccount")
	public String newaccountSubmit(@ModelAttribute UsernamePassword user, Model model) {
		if (!jdbcUserDetailsManager.userExists(user.getUsername())) {
			jdbcUserDetailsManager.createUser(User.withUsername(user.getUsername())
					.password(passwordEncoder.encode(user.getPassword())).roles(user.getRole()).build());
			
			
			Connexion connect = new Connexion();
			connect.setUsername(user.getUsername());
			connect.setMdp1(passwordEncoder.encode(user.getPassword()));
			userConnexionRepository.save(connect);

			
			model.addAttribute("greeting", user);
			return "result";
		} else {
			System.out.println("déja existant");
			String errorMessage = "email deja pris";
			model.addAttribute("error", errorMessage);
			model.addAttribute("user", user);
			return "newaccount";
		}
		// jdbcUserDetailsManager.
	}

	@GetMapping("/modif")
	public String modifyUserForm(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		UserProperties userProperties = new UserProperties();
		userProperties.setUsername(username);

		model.addAttribute("UserProperties", userProperties);
		return "modif";
	}
	
	@GetMapping("/logs")
	public String logs(Model model) {
		userloggins log = null;
		model.addAttribute("logs", log);
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		username = ((UserDetails) principal).getUsername();
		 
		
		List<userloggins> listeusers = userLogginsRepository.findByUsername(username);
		model.addAttribute("logs", listeusers);
		return "userloggins";
		
		}
	
	@GetMapping("/logins") //il faut enlever le s à loggin
		public String login(Model model){
		Connexion connect = null;
			int nbConnexion = 0;
			model.addAttribute("Connexion", connect);
			String username;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = ((UserDetails) principal).getUsername();
	
	Object Connexion = connect.getNbConnexion();
	
	nbConnexion = nbConnexion + 1 ;
	if (nbConnexion >= 3) {
		return "modif";
	}
	return "login";
	}
	
	@PostMapping("/modif")
	public String modifyAccountSubmit(@ModelAttribute UserProperties user, Model model)
	{
		
		System.out.printf("user.getOldPassword : %s , user.getNewPassword() : %s", user.getOldpassword(),user.getNewpassword());
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}
		
		jdbcUserDetailsManager.changePassword(user.getOldpassword(), passwordEncoder.encode(user.getNewpassword()));
		
		
		Connexion connect = userConnexionRepository.findByUsername(username);
		connect.setNbConnexion(0);
		connect.setMdp3(connect.getMdp2());
		connect.setMdp2(connect.getMdp1());
		connect.setMdp1(passwordEncoder.encode(user.getNewpassword()));
		
		userConnexionRepository.save(connect);
		
		//UserDetails userDetails = jdbcUserDetailsManager.loadUserByUsername(username);
		model.addAttribute("user", user);
		return ("hello");
		
	}

}
