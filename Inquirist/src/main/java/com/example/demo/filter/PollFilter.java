package com.example.demo.filter;

public class PollFilter
{
	private String ownerUsername;
	private String title;
	private int nbMinVotes;
	private int nbMaxVotes;

	public PollFilter(String ownerUsername, String title, int nbMinVotes, int nbMaxVotes)
	{
		super();
		this.ownerUsername = ownerUsername;
		this.title = title;
		this.nbMinVotes = nbMinVotes;
		this.nbMaxVotes = nbMaxVotes;
	}

	public PollFilter()
	{
		super();
		ownerUsername = "";
		title = "";
		nbMaxVotes = Integer.MAX_VALUE;
		nbMinVotes = 0;
	}

	/* --- Getters & Setters --- */

	public String getOwnerUsername()
	{
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername)
	{
		this.ownerUsername = ownerUsername;
	}

	public String getTitle()
	{
		return title;
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
