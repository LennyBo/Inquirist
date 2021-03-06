package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.Answer;
import com.example.demo.model.Poll;
import com.example.demo.model.User;
import com.example.demo.model.Vote;
import com.example.demo.model.VoteUser;
import com.example.demo.repository.AnswersRepository;
import com.example.demo.repository.PollsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.VoteUsersRepository;

@Controller
@RequestMapping("/vote")
public class VoteController
{

	@Autowired
	UsersRepository usersRepo;

	@Autowired
	AnswersRepository answersRepo;

	@Autowired
	VoteUsersRepository voteUserRepo;

	@Autowired
	PollsRepository pollsRepo;

	@PostMapping
	@PreAuthorize("hasAuthority('WRITER')")
	public RedirectView vote(@ModelAttribute(value = "vote") Vote vote, Map<String, Object> model)
	{
		Answer answer = answersRepo.findById(vote.getId()).get();
		Poll poll = answer.getPoll();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = usersRepo.findByUsername(auth.getName());

		// Check if the user has already voted for this poll
		List<VoteUser> votes = voteUserRepo.findAllByUser(user);
		for (VoteUser vu : votes)
		{
			if (poll == vu.getAnswer().getPoll())
			{
				return new RedirectView("/polls/result/" + answer.getPoll().getId());
			}
		}

		// Actual user add his vote
		VoteUser newVote = new VoteUser(user, answer, pollsRepo);
		voteUserRepo.save(newVote);

		return new RedirectView("/polls/result/" + answer.getPoll().getId());
	}
}
