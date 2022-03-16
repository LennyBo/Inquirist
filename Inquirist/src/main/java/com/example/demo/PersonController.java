package com.example.demo;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PersonController
{	
	@Autowired
	PersonnesRepository personsRepo;
	
	@GetMapping("/persons")
	public ModelAndView persons()
	{
		ModelAndView mav = new ModelAndView();
		mav.addObject("persons", personsRepo.findAll());
		return mav;
	}
	
	@GetMapping("persons/{id}")
	public String detail(@PathVariable("id") long id, Map<String, Object> model)
	{
		model.put("person", personsRepo.findById(id).get());
		return "detail";
	}

	@RequestMapping("persons/create")
	public String create(@ModelAttribute(value="person") Person person, Map<String, Object> model)
	{
		model.put("person", new Person());
		return "create";
	}
	
	@RequestMapping("persons/insert")
	public RedirectView insert(@ModelAttribute(value="person") Person person, Map<String, Object> model)
	{
		personsRepo.save(person);
		return new RedirectView("/persons");
	}

	@GetMapping("persons/remove/{id}")
	public RedirectView empDelete(@PathVariable("id") long id, Map<String, Object> model)
	{
		personsRepo.deleteById(id);
		return new RedirectView("/persons");
	}
}
