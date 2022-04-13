package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.SecurityToolBox;
import com.example.demo.model.Answer;
import com.example.demo.model.Poll;
import com.example.demo.model.User;
import com.example.demo.model.VoteUser;
import com.example.demo.repository.AnswersRepository;
import com.example.demo.repository.PollsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.VoteGuestsRepository;
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
	AnswersRepository answersRepo;

	@Autowired
	VoteUsersRepository voteusersRepo;

	@Autowired
	VoteGuestsRepository voteguestsRepo;

	@GetMapping
	@PreAuthorize("ROLE_ADMIN")
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
		Optional<User> u = usersRepo.findById(id);
		if (!u.isEmpty())
		{
			User user = u.get();

			model.put("user", user);

			List<VoteUser> votes = voteusersRepo.findAllByUser(user);
			List<Poll> polls = new ArrayList<Poll>();
			for (VoteUser vote : votes)
			{
				polls.add(vote.getAnswer().getPoll());
			}
			model.put("polls", polls);
		}

		return "user_detail";
	}

	@GetMapping("/create")
	public String create(@ModelAttribute(value = "user") User user, Map<String, Object> model)
	{
		model.put("user", new User());
		return "user_create";
	}

	@PostMapping("/insert")
	public RedirectView insert(@ModelAttribute(value = "user") User user, Map<String, Object> model)
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		String encodedPasswordConfirm = passwordEncoder.encode(user.getPasswordConfirm());
		user.setPassword(encodedPasswordConfirm);

		usersRepo.save(user);

		return new RedirectView("/polls");
	}

	@GetMapping("{id}/remove")
	public RedirectView deleteUser(@PathVariable("id") long id, Map<String, Object> model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (SecurityToolBox.containsRole(auth, "ROLE_ADMIN"))
		{
			Optional<User> u = usersRepo.findById(id);
			if (!u.isEmpty())
			{
				User user = u.get();
				deleteUser(user);
			}
		}

		return new RedirectView("/users");
	}

	private void deleteUser(User user)
	{
		// FIXME Delete on CASCADE user: refactor ?
		for (Poll poll : pollsRepo.findAllByOwner(user))
		{
			for (Answer answer : answersRepo.findAllByPoll(poll))
			{
				voteguestsRepo.deleteAllByAnswer(answer);
				voteusersRepo.deleteAllByAnswer(answer);
			}
			answersRepo.deleteAllByPoll(poll);
		}
		pollsRepo.deleteAllByOwner(user);
		usersRepo.delete(user);
	}
}
