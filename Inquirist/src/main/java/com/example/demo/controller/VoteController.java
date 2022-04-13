package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.Answer;
import com.example.demo.model.Poll;
import com.example.demo.model.User;
import com.example.demo.model.Vote;
import com.example.demo.model.VoteGuest;
import com.example.demo.model.VoteUser;
import com.example.demo.repository.AnswersRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.VoteGuestsRepository;
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
	VoteGuestsRepository voteGuestRepo;

	@PostMapping
	public RedirectView vote(@ModelAttribute(value = "vote") Vote vote, Map<String, Object> model)
	{
		System.out.println(vote);
		Answer answer = answersRepo.findById(vote.getId()).get();

		// crée le vote en fonction du user ou du guest
		if (true)
		{
			// mettre l'utilsateur login
			User user = usersRepo.findById((long) 1).get();
			VoteUser newVote = new VoteUser(user, answer);
			voteUserRepo.save(newVote);

		}
		else
		{

		}
		return new RedirectView("/polls/result/"+answer.getPoll().getId());
	}

}
