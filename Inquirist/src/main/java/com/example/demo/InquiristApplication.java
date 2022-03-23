package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Category;
import com.example.demo.model.Person;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.PersonsRepository;

@SpringBootApplication
@Configuration
public class InquiristApplication
{
	@Autowired
	PersonsRepository personsRepo;
	
	@Autowired
	CategoriesRepository categoriesRepo;

	public static void main(String[] args)
	{
		SpringApplication.run(InquiristApplication.class, args);
	}

	@PostConstruct
	public void init()
	{
		for (int i = 0; i < 10; i++)
		{
			Category c = new Category("Categorie" + i);
			categoriesRepo.save(c);
			Person p = new Person("Chevre" + i, "Sebastien" + i, c);
			personsRepo.save(p);
		}
	}
}
