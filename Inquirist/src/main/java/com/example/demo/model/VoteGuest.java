package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class VoteGuest {
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
	}

	@Override
	public String toString() {
		return "GuestVote [id=" + id + ", poll=" + poll + ", guest=" + guest + ", answer=" + answer + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
		
	}