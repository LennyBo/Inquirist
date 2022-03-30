package com.example.demo.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.demo.repository.VoteUsersRepository;

import lombok.Data;

@Data
@Entity
public class Poll
{
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	private String title;
	private String description;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@ManyToOne
	@JoinColumn(name = "ownerId")
	private User owner;

	public Poll(String title, String description, Date startDate, User owner)
	{
		super();
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.owner = owner;
	}

	public List<User> getParticipants(VoteUsersRepository voteusersRepo)
	{
		List<VoteUser> voteUsers = voteusersRepo.findAllByPoll(this);
		List<User> users = new LinkedList<User>();
		for (VoteUser voteUser : voteUsers)
			users.add(voteUser.getUser());
		return users;
	}

	public Poll()
	{
		super();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public User getOwner()
	{
		return owner;
	}

	public void setOwner(User owner)
	{
		this.owner = owner;
	}
}
