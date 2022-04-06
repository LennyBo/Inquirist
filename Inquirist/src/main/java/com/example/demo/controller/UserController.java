package com.example.demo.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.Answer;
import com.example.demo.model.Poll;
import com.example.demo.model.User;
import com.example.demo.model.VoteUser;
import com.example.demo.repository.PollsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.VoteUsersRepository;

@Controller
@RequestMapping("/users")
public class UserController
{
	@Autowired
	UsersRepository usersRepo;

	@Autowired
	PollsRepository pollsRepo;
	
	@Autowired
	VoteUsersRepository voteusersRepository;

	@GetMapping
	public ModelAndView users()
	{
		// TODO only if admin
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", usersRepo.findAll());
		return mav;
	}

	@GetMapping("/{id}")
	public String detail(@PathVariable("id") long id, Map<String, Object> model)
	{
		User user = usersRepo.findById(id).get();
		model.put("user", user);
		List<VoteUser> votes = voteusersRepository.findAllByUser(user);
		List<Poll> polls = new ArrayList<Poll>();
		for(VoteUser vote : votes)
		{
			polls.add(vote.getAnswer().getPoll());
		}
		model.put("polls", polls);
//		model.put("polls", user.getParticipatedPolls(voteusersRepository));
//		model.put("polls", user.getOwnedPolls(pollsRepo));
		return "user_detail";
	}

	@GetMapping("/{id}/remove")
	public RedirectView delete(@PathVariable("id") long id, Map<String, Object> model)
	{
		// TODO only if admin
		usersRepo.deleteById(id);
		return new RedirectView("/users");
	}
}
