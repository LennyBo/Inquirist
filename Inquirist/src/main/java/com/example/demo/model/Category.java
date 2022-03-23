package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
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
}