package com.example.demo.filter;

public class PollFilter
{
	private String title;
	private String description;
	private int nbMinVotes;
	private int nbMaxVotes;

	public PollFilter(String title, String description, int nbMinVotes, int nbMaxVotes)
	{
		super();
		this.title = title;
		this.description = description;
		this.nbMinVotes = nbMinVotes;
		this.nbMaxVotes = nbMaxVotes;
	}

	public PollFilter()
	{
		super();
		description = "";
		title = "";
		nbMaxVotes = Integer.MAX_VALUE;
		nbMinVotes = 0;
	}

	/* --- Getters & Setters --- */

	public String getTitle()
	{
		return title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getNbMinVotes()
	{
		return nbMinVotes;
	}

	public void setNbMinVotes(int nbMinVotes)
	{
		this.nbMinVotes = nbMinVotes;
	}

	public int getNbMaxVotes()
	{
		return nbMaxVotes;
	}

	public void setNbMaxVotes(int nbMaxVotes)
	{
		this.nbMaxVotes = nbMaxVotes;
	}
}
