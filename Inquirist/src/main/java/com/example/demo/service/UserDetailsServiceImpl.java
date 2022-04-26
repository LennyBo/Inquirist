package com.example.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	@Autowired
	private UsersRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = userRepository.findByUsername(username);

		if (user == null)
		{
			// TODO Do something
			return null;
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		switch (user.getRole()) // Could be refactored, but it's to be clear
		{
			case READER:
				grantedAuthorities.add(new SimpleGrantedAuthority(Role.READER.name()));
				break;
			case WRITER:
				grantedAuthorities.add(new SimpleGrantedAuthority(Role.READER.name()));
				grantedAuthorities.add(new SimpleGrantedAuthority(Role.WRITER.name()));
				break;
			case ADMIN:
				grantedAuthorities.add(new SimpleGrantedAuthority(Role.READER.name()));
				grantedAuthorities.add(new SimpleGrantedAuthority(Role.WRITER.name()));
				grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.name()));
				break;
			default:
				grantedAuthorities.add(new SimpleGrantedAuthority(Role.READER.name()));
				break;
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}
}
