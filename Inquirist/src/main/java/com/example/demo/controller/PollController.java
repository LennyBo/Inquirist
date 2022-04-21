package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.SecurityToolBox;
import com.example.demo.filter.PollFilter;
import com.example.demo.model.Answer;
import com.example.demo.model.Poll;
import com.example.demo.model.User;
import com.example.demo.model.Vote;
import com.example.demo.repository.AnswersRepository;
import com.example.demo.repository.PollsRepository;
import com.example.demo.repository.UsersRepository;
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

	@GetMapping
	@PreAuthorize("hasAuthority('READER')")
	public String polls(Map<String, Object> model, @RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "") String description, @RequestParam(defaultValue = "0") int nbMinVotes, @RequestParam(defaultValue = Integer.MAX_VALUE + "") int nbMaxVotes, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size)
	{
		PollFilter filter = new PollFilter(title, description, nbMinVotes, nbMaxVotes);

		Page<Poll> pagedResult = pollsRepo.findAllByTitleContainingAndDescriptionContainingAndNbVotesBetween(title, description, nbMinVotes, nbMaxVotes, PageRequest.of(page - 1, size));

		model.put("pollsPage", pagedResult);
		model.put("filter", filter);

		int totalPages = pagedResult.getTotalPages();
		if (totalPages > 0)
		{
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.put("pageNumbers", pageNumbers);
		}

		return "polls";
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('READER')")
	public String detail(@PathVariable("id") long id, Map<String, Object> model)
	{
		model.put("poll", pollsRepo.findById(id).get());

		Object[] answers = answersRepo.findAllByPollId(id).toArray();
		model.put("answers", answers);

		model.put("vote", new Vote());

		return "poll_detail";
	}

	@GetMapping("/create")
	@PreAuthorize("hasAuthority('WRITER')")
	public String create(@ModelAttribute(value = "poll") Poll poll, Map<String, Object> model)
	{
		return "poll_create";
	}

	@PostMapping("/insert")
	@PreAuthorize("hasAuthority('WRITER')")
	public RedirectView insert(@ModelAttribute(value = "poll") Poll poll, Map<String, Object> model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User owner = usersRepo.findByUsername(auth.getName());

		poll.setOwner(owner);
		poll.setStartDate(new Date(System.currentTimeMillis()));
		String[] answers = poll.getAnswersStringList();

		if (Poll.Valid(poll) && answers.length > 1)
		{
			pollsRepo.save(poll);
			for (int i = 0; i < answers.length; i++)
			{
				Answer answer = new Answer(poll, answers[i]);
				answersRepo.save(answer);
			}
		}

		return new RedirectView("/polls");
	}

	@GetMapping("/{id}/remove")
	@PreAuthorize("hasAuthority('WRITER')")
	public RedirectView removePoll(@PathVariable("id") long id, Map<String, Object> model)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<Poll> p = pollsRepo.findById(id);
		if (!p.isEmpty())
		{
			Poll poll = p.get();

			if (SecurityToolBox.containsRole(auth, "WRITER"))
			{
				User user = usersRepo.findByUsername(auth.getName());

				if (poll.getOwner().getId() == user.getId())
				{
					deletePoll(poll);
				}
			}
			else if (SecurityToolBox.containsRole(auth, "ADMIN"))
			{
				deletePoll(poll);
			}
		}

		return new RedirectView("/polls");
	}

	@GetMapping("/result/{id}")
	@PreAuthorize("hasAuthority('READER')")
	public String resultPoll(@PathVariable("id") long id, Map<String, Object> model)
	{
		model.put("poll", pollsRepo.findById(id).get());
		List<Answer> answers = answersRepo.findAllByPollId(id);
		for (Answer answer : answers)
		{
			answer.setNbVote(numberOfVoteForAnswer(answer));
		}
		model.put("answers", answers.toArray());

		return "poll_result";
	}

	private void deletePoll(Poll poll)
	{
		// FIXME Delete on CASCADE answers: refactor ?
		for (Answer answer : answersRepo.findAllByPoll(poll))
		{
			voteusersRepo.deleteAllByAnswer(answer);
		}
		answersRepo.deleteAllByPoll(poll);
		pollsRepo.delete(poll);
	}

	private int numberOfVoteForAnswer(Answer answer)
	{
		return voteusersRepo.findByAnswer(answer).size();
	}
}
