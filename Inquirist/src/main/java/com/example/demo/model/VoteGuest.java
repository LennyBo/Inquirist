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
public class VoteGuest
{
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "pollId")
	private Poll poll;

	@ManyToOne
	@JoinColumn(name = "guestId")
	private Guest guest;

	@ManyToOne
	@JoinColumn(name = "answerId")
	private Answer answer;

	public VoteGuest(Poll poll, Guest guest, Answer answer)
	{
		super();
		this.poll = poll;
		this.guest = guest;
		this.answer = answer;
	}

	public VoteGuest()
	{
		super();
	}
}