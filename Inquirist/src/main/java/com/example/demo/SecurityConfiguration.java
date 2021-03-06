package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests().antMatchers("/h2/**").permitAll(); // FIXME
		http.csrf().disable(); // FIXME
		http.headers().frameOptions().disable(); // FIXME

		http.authorizeRequests() //
				.antMatchers("/login", "/signup").permitAll() //
				.antMatchers("/users/**").fullyAuthenticated() //
				.antMatchers("/polls/**").fullyAuthenticated() //
				.and().formLogin().loginPage("/login")//.loginProcessingUrl("/do_login").defaultSuccessUrl("/", true) //
				.and().logout().logoutSuccessUrl("/");
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception
	{
		return authenticationManager();
	}
}
