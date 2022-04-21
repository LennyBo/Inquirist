package com.example.demo.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.filter.UserFilter;
import com.example.demo.model.Answer;
import com.example.demo.model.Poll;
import com.example.demo.model.User;
import com.example.demo.model.VoteUser;
import com.example.demo.repository.AnswersRepository;
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
	AnswersRepository answersRepo;

	@Autowired
	VoteUsersRepository voteusersRepo;

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public String users(Map<String, Object> model, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize)
	{
//		Pageable paging = PageRequest.of(pageNo, pageSize);
//		Page<User> pagedResult = usersRepo.findAll(paging);
//
//		if (pagedResult.hasContent())
//		{
//			model.put("users", pagedResult.getContent());
//		}
//		else
//		{
//			model.put("users", new ArrayList<User>());
//		}

		model.put("users", usersRepo.findAll());
		model.put("filter", new UserFilter());
		return "users";
	}

	@PostMapping("/filter")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String usersFiltered(@ModelAttribute(value = "filter") UserFilter filter, Map<String, Object> model)
	{
		model.put("users", usersRepo.findAllByUsernameContaining(filter.getUsername()));
		model.put("filter", filter);
		return "users";
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('READER')")
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

	@PostMapping("{id}/remove")
	@PreAuthorize("hasAuthority('ADMIN')")
	public RedirectView deleteUser(@PathVariable("id") long id, Map<String, Object> model)
	{
		Optional<User> u = usersRepo.findById(id);
		if (!u.isEmpty())
		{
			User user = u.get();
			deleteUser(user);
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
				voteusersRepo.deleteAllByAnswer(answer);
			}
			answersRepo.deleteAllByPoll(poll);
		}
		pollsRepo.deleteAllByOwner(user);
		usersRepo.delete(user);
	}
}
