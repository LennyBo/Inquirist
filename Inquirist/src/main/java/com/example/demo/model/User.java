package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User
{
	private String username;
	private String password;

	@Column(unique = true)
	private String name;

	private Boolean isAdmin;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	public User(String username, String name, String password, Boolean isAdmin)
	{
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.isAdmin = isAdmin;
	}

	public User()
	{
		super();
	}
}
