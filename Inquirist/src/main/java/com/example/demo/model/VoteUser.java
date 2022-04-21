package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.repository.PollsRepository;

import lombok.Data;

@Data
@Entity
public class VoteUser
{
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne
	@JoinColumn(name = "answerId")
	private Answer answer;

	public VoteUser(User user, Answer answer, PollsRepository pollsRepo)
	{
		super();
		this.user = user;
		this.answer = answer;

		answer.getPoll().addVote();
		pollsRepo.save(answer.getPoll());
	}

	public VoteUser()
	{
		super();
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

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Answer getAnswer()
	{
		return answer;
	}

	public void setAnswer(Answer answer)
	{
		this.answer = answer;

		answer.getPoll().addVote();
	}
}
