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
	@JoinColumn(name = "guestId")
	private Guest guest;

	@ManyToOne
	@JoinColumn(name = "answerId")
	private Answer answer;

	public VoteGuest(Guest guest, Answer answer)
	{
		super();
		this.guest = guest;
		this.answer = answer;
	}

	public VoteGuest()
	{
		super();
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "VoteGuest [id=" + id + ", guest=" + guest + ", answer=" + answer + "]";
	}
	
}