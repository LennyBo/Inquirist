package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity // définit une entité jpa
@Table(name = "tbl_personne") // optionnel, si pas spécifié prend le nom de l'entité
public class Person
{
	@Column(name = "nom") // si pas spécifié prend le nom du champ
	private String nom;

	@Column(name = "prenom") // si pas spécifié prend le nom du champ
	private String prenom;

	@OneToOne
	@JoinColumn(name = "categorie_id")
	private Category categorie;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	public Person(String nom, String prenom, Category categorie)
	{
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.categorie = categorie;
	}

	public Person()
	{
		super();
	}
}