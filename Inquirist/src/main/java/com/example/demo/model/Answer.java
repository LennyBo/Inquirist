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
public class Answer
{
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "pollId")
	private Poll poll;

	private String answer;

	public Answer(Poll poll, String answer)
	{
		super();
		this.poll = poll;
		this.answer = answer;
	}

	public Answer()
	{
		super();
	}
}
