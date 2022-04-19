package com.example.demo.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Vote
{
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	public Vote()
	{

	}

	@Override
	public String toString()
	{
		return "Vote [id=" + id + "]";
	}

	/* --- Getters & Setters --- */

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}
}
