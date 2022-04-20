package com.example.demo.model;

import java.beans.Transient;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.repository.PollsRepository;
import com.example.demo.repository.VoteUsersRepository;

import lombok.Data;

@Data
@Entity
public class User
{
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String passwordConfirm;
	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.ORDINAL)
	private Role role;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	public User(String username, String name, String password, String passwordConfirm, Role role)
	{
		super();
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.name = name;
		this.role = role;
	}

	public User()
	{
		super();
	}

	public List<Poll> getOwnedPolls(PollsRepository pollsRepo)
	{
		return pollsRepo.findAllByOwner(this);
	}

	public List<Poll> getParticipatedPolls(VoteUsersRepository voteusersRepo)
	{
		List<VoteUser> voteUsers = voteusersRepo.findAllByUser(this);
		List<Poll> polls = new LinkedList<Poll>();
		for (VoteUser voteUser : voteUsers)
			polls.add(voteUser.getAnswer().getPoll());
		return polls;
	}

	public void encryptPasswords()
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		password = passwordEncoder.encode(password);
		passwordConfirm = passwordEncoder.encode(passwordConfirm);
	}
	
	@Override
	public String toString()
	{
		return username + " (name=" + name + ", role=" + role + ")";
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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Transient
	public String getPasswordConfirm()
	{
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm)
	{
		this.passwordConfirm = passwordConfirm;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
