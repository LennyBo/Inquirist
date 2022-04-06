package com.example.demo.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.demo.repository.PollsRepository;
import com.example.demo.repository.VoteUsersRepository;

import lombok.Data;

@Data
@Entity
public class User
{
	@Column(unique = true)
	private String username;
	private String password;
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Boolean getIsAdmin()
	{
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin)
	{
		this.isAdmin = isAdmin;
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
