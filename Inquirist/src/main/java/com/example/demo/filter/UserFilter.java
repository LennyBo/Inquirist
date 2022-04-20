package com.example.demo.filter;

public class UserFilter
{
	private String username;

	public UserFilter(String username)
	{
		super();
		this.username = username;
	}

	public UserFilter()
	{
		super();
	}

	@Override
	public String toString()
	{
		return username;
	}

	/* --- Getters & Setters --- */

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
}
