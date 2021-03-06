package com.example.demo.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
	private int nbVotes;
	private String answersString;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@ManyToOne(cascade = CascadeType.DETACH)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JoinColumn(name = "ownerId")
	private User owner;

	public Poll(String title, String description, User owner)
	{
		super();
		this.title = title;
		this.description = description;
		this.startDate = new Date(System.currentTimeMillis());
		this.owner = owner;
		nbVotes = 0;
	}

	public Poll(User owner)
	{
		super();
		this.startDate = new Date(System.currentTimeMillis());
		this.owner = owner;
	}

	public Poll()
	{
	}

	public void addVote()
	{
		nbVotes++;
	}

	public static boolean Valid(Poll poll)
	{
		if (poll.getTitle() == null || poll.getTitle() == "")
			return false;
		return true;
	}

	public String[] getAnswersStringList()
	{
		if (answersString == null)
		{
			return new String[] { "yes", "no" };
		}
		return answersString.split(";");
	}

	@Override
	public String toString()
	{
		return "Poll [id=" + id + ", title=" + title + ", description=" + description + ", startDate=" + startDate + ", owner=" + owner + "]";
	}

	/* --- Getters & Setters --- */

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public int getNbVotes()
	{
		return nbVotes;
	}

	public void setNbVotes(int nbVotes)
	{
		this.nbVotes = nbVotes;
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

	public String getAnswersString()
	{
		return answersString;
	}

	public void setAnswersString(String answersString)
	{
		this.answersString = answersString;
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
