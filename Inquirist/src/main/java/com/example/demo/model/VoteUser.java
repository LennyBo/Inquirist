package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

	public VoteUser(User user, Answer answer)
	{
		super();
		this.user = user;
		this.answer = answer;
	}

	public VoteUser()
	{
		super();
	}
}
