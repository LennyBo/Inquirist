package com.example.demo.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Poll {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private String title;
	private String description;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
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
	}
	
	

	@Override
	public String toString() {
		return "Poll [id=" + id + ", title=" + title + ", description=" + description + ", startDate=" + startDate
				+ ", owner=" + owner + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	


	

}
