package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.example.demo.repository.AnswersRepository;
import com.example.demo.repository.PollsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.VoteGuestsRepository;
import com.example.demo.repository.VoteUsersRepository;

@Controller
@RequestMapping("/polls")
public class PollController
{
	@Autowired
	PollsRepository pollsRepo;

	@Autowired
	AnswersRepository answersRepo;

	@Autowired
	UsersRepository usersRepo;

	@Autowired
	VoteUsersRepository voteusersRepo;

	@Autowired
	VoteGuestsRepository voteguestsRepo;

	@GetMapping
	public String polls(Map<String, Object> model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (SecurityToolBox.containsRole(auth, "ROLE_USER"))
		{
			User user = usersRepo.findByUsername(auth.getName());
			model.put("polls", pollsRepo.findAllByOwner(user));
		}
		else if (SecurityToolBox.containsRole(auth, "ROLE_ADMIN"))
		{
			model.put("polls", pollsRepo.findAll());
		}

		return "polls";

	}

	@GetMapping("/{id}")
	public String detail(@PathVariable("id") long id, Map<String, Object> model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (SecurityToolBox.containsRole(auth, "ROLE_USER"))
		{
			// TODO Verify if the user has the the right
			model.put("poll", pollsRepo.findById(id).get());

			Object[] answers = answersRepo.findAllByPollId(id).toArray();
			model.put("answers", answers);
		}
		else if (SecurityToolBox.containsRole(auth, "ROLE_ADMIN"))
		{
			model.put("poll", pollsRepo.findById(id).get());

			Object[] answers = answersRepo.findAllByPollId(id).toArray();
			model.put("answers", answers);
		}

		return "poll_detail";
	}

	@GetMapping("/create")
	public String create(@ModelAttribute(value = "poll") Poll poll, Map<String, Object> model)
	{
		return "poll_create";
	}

	@PostMapping("/insert")
	public RedirectView insert(@ModelAttribute(value = "poll") Poll poll, Map<String, Object> model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null)
		{
			User owner = usersRepo.findByUsername(auth.getName());

			poll.setOwner(owner);
			poll.setStartDate(new Date(System.currentTimeMillis()));
			pollsRepo.save(poll);

			String[] answers = poll.getAnswersStringList();
			for (int i = 0; i < answers.length; i++)
			{
				Answer answer = new Answer(poll, answers[i]);
				answersRepo.save(answer);
			}
		}

		return new RedirectView("/polls");
	}

	@GetMapping("/{id}/remove")
	public RedirectView removePoll(@PathVariable("id") long id, Map<String, Object> model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (SecurityToolBox.containsRole(auth, "ROLE_USER"))
		{
			Optional<Poll> p = pollsRepo.findById(id);
			if (!p.isEmpty())
			{
				Poll poll = p.get();
				User user = usersRepo.findByUsername(auth.getName());

				if (poll.getOwner().getId() == user.getId())
				{
					// FIXME Delete on CASCADE answers: refactor ?
					for (Answer answer : answersRepo.findAllByPoll(poll))
					{
						voteguestsRepo.deleteAllByAnswer(answer);
						voteusersRepo.deleteAllByAnswer(answer);
					}
					answersRepo.deleteAllByPoll(poll);
					pollsRepo.delete(poll);
				}
			}
		}
		else if (SecurityToolBox.containsRole(auth, "ROLE_ADMIN"))
		{
			Optional<Poll> p = pollsRepo.findById(id);
			if (!p.isEmpty())
			{
				Poll po = p.get();
				po.setOwner(null);
				pollsRepo.delete(po);
			}
		}

		return new RedirectView("/polls");
	}
}
