package com.example.securingweb;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

@Component
 public class AuthenticationEvents {
	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;

	@Autowired
	UserLogginsRepository userLogginsRepository;
	
	
	@Autowired
	UserLogginsRepository userConnexionRepository;
	
	
	//@Autowired
	//UserLogFailuresRepository userLogFailuresRepository;

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpServletResponse response;

	int failure = 0;
	
	/* X-Forwarded-For: clientIpAddressOriginal, proxy1, proxy2 */
	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}

	@EventListener
	public void onSuccess(AuthenticationSuccessEvent success) {
		System.out.println("Youhouh!");
		
		System.out.println("request : " + request.getRequestURI());

		Object principal = success.getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		userloggins userLoggins = new userloggins();
		userLoggins.setConnectionDate(new Date().toString());
		userLoggins.setUsername(username);
		userLoggins.setUserAction("SUCCESS");
		userLoggins.setUserIP(getClientIP());
		userLogginsRepository.save(userLoggins);
	}

	@EventListener
	public void onFailure(AbstractAuthenticationFailureEvent failures) {
		System.out.println("Pfffff!");
		failure = failure +1;
		if (failure == 3) {
			System.out.println("3 connexions ratées" + " " + getClientIP());
			failure =0;
		}
		
		System.out.println("request : " + request.getRequestURI());
		Object principal = failures.getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		userloggins userLoggins = new userloggins();
		userLoggins.setConnectionDate(new Date().toString());
		userLoggins.setUsername(username);
		userLoggins.setUserAction("Connexion réfusée");
		userLoggins.setUserIP(getClientIP());
		userLogginsRepository.save(userLoggins);
	}

	@EventListener
	public void onLogout(LogoutSuccessEvent logout) {
		// ...
		System.out.println("logged out 2222!");
		System.out.println("request : " + request.getRequestURI());
		Object principal = logout.getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		userloggins userLoggins = new userloggins();
		userLoggins.setConnectionDate(new Date().toString());
		userLoggins.setUsername(username);
		userLoggins.setUserAction("Logout");
		userLoggins.setUserIP(getClientIP());
		userLogginsRepository.save(userLoggins);
	}

	@EventListener
	public void onRequestAuthenticationEvent(AbstractAuthenticationEvent request) throws IOException {
		System.out.println("-- AbstractAuthenticationEvent --");
		System.out.println(request);
		Object principal = request.getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}


	}

	@EventListener
	public void handleRequestEvent(RequestHandledEvent request) {
		System.out.println("-- RequestHandledEvent --");
		System.out.println(request);
	}
}