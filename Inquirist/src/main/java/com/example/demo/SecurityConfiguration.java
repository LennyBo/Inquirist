package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication() // spécifie à Spring que l'authentification se fera "en mémoire"
				.withUser("user").password("{noop}password").roles("USER"); // {noop}, pas de cryptage
		auth.userDetailsService(userDetailsService).passwordEncoder(new Encoder().passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests().antMatchers("/h2/**")
				.permitAll();
		
		http.csrf().disable(); //FIXME
		
		http.headers().frameOptions().disable(); // FIXME

		http.authorizeRequests().antMatchers("/polls").authenticated()
				// .antMatchers("/polls").authenticated()
				.and().formLogin();
	}
}
