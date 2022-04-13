package com.example.demo;

import org.springframework.security.core.Authentication;

public class SecurityToolBox
{
	public static boolean containsRole(Authentication auth, String role)
	{
		return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role));
	}
}
