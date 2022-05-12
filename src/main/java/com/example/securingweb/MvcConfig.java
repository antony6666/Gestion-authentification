package com.example.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/newaccount").setViewName("newaccount");
		registry.addViewController("/admin").setViewName("admin");
		registry.addViewController("/user").setViewName("user");
		registry.addViewController("/modif").setViewName("modif");
		registry.addViewController("/logs").setViewName("userloggins");
	}

}