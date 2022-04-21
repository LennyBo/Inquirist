package com.example.demo;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.model.Answer;
import com.example.demo.model.Poll;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.VoteUser;
import com.example.demo.repository.AnswersRepository;
import com.example.demo.repository.PollsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.VoteUsersRepository;

@SpringBootApplication
@Configuration
public class InquiristApplication
{
	@Autowired
	UsersRepository usersRepo;

	@Autowired
	PollsRepository pollsRepo;

	@Autowired
	AnswersRepository answersRepo;

	@Autowired
	VoteUsersRepository voteusersRepo;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args)
	{
		SpringApplication.run(InquiristApplication.class, args);
	}

	@PostConstruct
	public void init()
	{
		int nbUsers = 10;
		int nbPollsPerUser = 5;

		List<User> users = new LinkedList<User>();

		for (int i = 0; i < nbUsers; i++)
		{
			/* --- Users --- */
			Role role = Role.READER;
			if (i % 3 == 0)
			{
				role = Role.ADMIN;
			}
			else if (i % 2 == 0)
			{
				role = Role.WRITER;
			}

			User user = new User("username" + i, "name" + i, "mdp", "mdp", role);
			usersRepo.save(user);
			users.add(user);

			/* --- Polls --- */
			for (int j = 0; j < nbPollsPerUser; j++)
			{
				int number = i * nbUsers + j;
				Poll poll = new Poll("sondage" + number, "description" + number, user);
				pollsRepo.save(poll);

				/* --- Answers --- */
				Answer oui = new Answer(poll, "oui");
				Answer non = new Answer(poll, "non");
				answersRepo.save(oui);
				answersRepo.save(non);

				for (User u : users)
				{
					/* --- Votes --- */
					VoteUser vote;
					if (u.getId() % 2 == 0)
						vote = new VoteUser(u, non, pollsRepo);
					else
						vote = new VoteUser(u, oui, pollsRepo);
					voteusersRepo.save(vote);
				}
			}
		}
	}
}
