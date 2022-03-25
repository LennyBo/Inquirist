package com.example.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.Poll;
import com.example.demo.repository.PollsRepository;

@Controller
@RequestMapping("/polls")
public class PollController {

	@Autowired
	PollsRepository pollsRepo;

	@GetMapping
	public String polls(Map<String, Object> model) {
		model.put("polls", pollsRepo.findAll());
		for (Poll p : pollsRepo.findAll()) {
			System.out.println(p);
		}
		return "polls";
	}
	
	@GetMapping("/{id}/detail")
	public RedirectView detail(@PathVariable("id") long id, Map<String, Object> model)
	{
		pollsRepo.deleteById(id);
		return new RedirectView("");
	}
	
	@GetMapping("/{id}/remove")
	public RedirectView remove(@PathVariable("id") long id, Map<String, Object> model)
	{
		Optional<Poll> p = pollsRepo.findById(id);
		if (!p.isEmpty()) {
			Poll po = p.get();
			po.setOwner(null);
			pollsRepo.delete(po);
		}
		return new RedirectView("/polls");
	}

}
