package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

	public Poll()
	{
		super();
	}
}
