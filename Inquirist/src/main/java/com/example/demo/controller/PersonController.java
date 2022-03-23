package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonsRepository;

@Controller
@RequestMapping("/persons")
public class PersonController
{	
	@Autowired
	PersonsRepository personsRepo;
	
	@GetMapping
	public ModelAndView persons()
	{
		ModelAndView mav = new ModelAndView();
		mav.addObject("persons", personsRepo.findAll());
		return mav;
	}
	
	@GetMapping("/{id}")
	public String detail(@PathVariable("id") long id, Map<String, Object> model)
	{
		model.put("person", personsRepo.findById(id).get());
		return "person_detail";
	}

	@GetMapping("/create")
	public String create(@ModelAttribute(value="person") Person person, Map<String, Object> model)
	{
		model.put("person", new Person());
		return "person_create";
	}
	
	@PostMapping("/insert")
	public RedirectView insert(@ModelAttribute(value="person") Person person, Map<String, Object> model)
	{
		personsRepo.save(person);
		return new RedirectView("/persons");
	}
	
	@GetMapping("/{id}/remove")
	public RedirectView empDelete2(@PathVariable("id") long id, Map<String, Object> model)
	{
		personsRepo.deleteById(id);
		return new RedirectView("/persons");
	}
}
