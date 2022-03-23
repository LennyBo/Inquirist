package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Category
{
	private String nom;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	public Category(String nom)
	{
		super();
		this.nom = nom;
	}

	public Category()
	{
		super();
	}

	@Override
	public String toString()
	{
		return "Category [nom=" + nom + ", id=" + id + "]";
	}

	public String getNom()
	{
		return nom;
	}

	public void setNom(String nom)
	{
		this.nom = nom;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}