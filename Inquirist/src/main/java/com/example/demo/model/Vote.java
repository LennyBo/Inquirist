package com.example.demo.model;

public class Vote {
	private long id;
	public Vote()
	{
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Vote [id=" + id + "]";
	}
	

}
