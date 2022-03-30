package com.example.demo.controller;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.Answer;
import com.example.demo.model.Person;
import com.example.demo.model.Poll;
import com.example.demo.model.User;
import com.example.demo.repository.AnswersRepository;
import com.example.demo.repository.PersonsRepository;
import com.example.demo.repository.PollsRepository;
import com.example.demo.repository.UsersRepository;

@Controller
@RequestMapping("/polls")
public class PollController {

	@Autowired
	PollsRepository pollsRepo;

	@Autowired
	AnswersRepository answersRepo;
	
	@Autowired
	UsersRepository usersRepo;

	@GetMapping
	public String polls(Map<String, Object> model) {
		model.put("polls", pollsRepo.findAll());
		for (Poll p : pollsRepo.findAll()) {
			//System.out.println(p);
		}
		return "polls";
	}
	
	@GetMapping("/{id}")
	public String detail(@PathVariable("id") long id, Map<String, Object> model)
	{
		model.put("poll", pollsRepo.findById(id).get());
		
		Object[] answers = answersRepo.findByPollId(id).toArray();
		model.put("answers", answers);
		return "poll_detail";
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

	@GetMapping("/create")
	public String create(@ModelAttribute(value="poll") Poll poll, Map<String, Object> model)
	{
		return "poll_create";
	}
	
	@PostMapping("/insert")
	public RedirectView insert(@ModelAttribute(value="poll") Poll poll, Map<String, Object> model)
	{
		//modifer le owner avec la personne login sinon retouner une erreur
		User owner = usersRepo.findById((long) 1).get();
		poll.setOwner(owner);
		poll.setStartDate(new Date(System.currentTimeMillis()));
		pollsRepo.save(poll);

		
		String[] answers = poll.getAnswersStringList(); 
		for(int i=0; i<answers.length; i++)
		{
			Answer answer = new Answer(poll, answers[i]);
			answersRepo.save(answer);
		}		
		

		return new RedirectView("/polls");
	}
	
	
}
