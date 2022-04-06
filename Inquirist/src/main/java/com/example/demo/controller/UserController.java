package com.example.demo.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.SecurityToolBox;
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
	public String users(Map<String, Object> model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (SecurityToolBox.containsRole(auth, "ROLE_ADMIN"))
		{
			model.put("users", usersRepo.findAll());
		}

		return "users";
	}

	@GetMapping("/{id}")
	public String detail(@PathVariable("id") long id, Map<String, Object> model)
	{
		User user = usersRepo.findById(id).get();
		model.put("user", user);
		model.put("polls", user.getParticipatedPolls(voteusersRepository));
//		model.put("polls", user.getOwnedPolls(pollsRepo));
		return "user_detail";
	}

	@GetMapping("{id}/remove")
	public RedirectView deleteUser(@PathVariable("id") long id, Map<String, Object> model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (SecurityToolBox.containsRole(auth, "ROLE_ADMIN"))
		{
			usersRepo.deleteById(id);
		}

		return new RedirectView("/users");
	}
}
