package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Answer;
import com.example.demo.model.Guest;
import com.example.demo.model.Poll;
import com.example.demo.model.User;
import com.example.demo.model.VoteGuest;
import com.example.demo.model.VoteUser;
import com.example.demo.repository.AnswersRepository;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.GuestsRepository;
import com.example.demo.repository.PollsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.VoteGuestsRepository;
import com.example.demo.repository.VoteUsersRepository;

@SpringBootApplication
@Configuration
public class InquiristApplication
{
	@Autowired
	CategoriesRepository categoriesRepo;

	@Autowired
	UsersRepository usersRepo;

	@Autowired
	PollsRepository pollsRepo;

	@Autowired
	AnswersRepository answersRepo;

	@Autowired
	GuestsRepository guestsRepo;

	@Autowired
	VoteUsersRepository voteusersRepo;

	@Autowired
	VoteGuestsRepository voteguestsRepo;

	public static void main(String[] args)
	{
		SpringApplication.run(InquiristApplication.class, args);
	}

	@PostConstruct
	public void init()
	{
		User mat = new User("user", "mat", "mdp", true);
		usersRepo.save(mat);

		Guest mat2 = new Guest("mat2");
		guestsRepo.save(mat2);

		for (int i = 0; i < 5; i++)
		{
			Poll p = new Poll("salut", "sondage" + i, mat);
			pollsRepo.save(p);

			Answer oui = new Answer(p, "oui");
			Answer non = new Answer(p, "non");

			answersRepo.save(oui);
			answersRepo.save(non);
			
			VoteUser vu = new VoteUser(mat, oui);
			VoteGuest vg = new VoteGuest(mat2, non);
			
			voteusersRepo.save(vu);
			voteguestsRepo.save(vg);

		}

		usersRepo.save(mat);
	}
}
