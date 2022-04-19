package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
//		User mat = new User("user", "mat", "mdp", "mdp", Role.ADMIN);
//		mat.setPasswordConfirm(null);
//		usersRepo.save(mat);
//
//		for (int i = 0; i < 5; i++)
//		{
//			Poll p = new Poll("salut", "sondage" + i, mat);
//			pollsRepo.save(p);
//
//			Answer oui = new Answer(p, "oui");
//			Answer non = new Answer(p, "non");
//
//			answersRepo.save(oui);
//			answersRepo.save(non);
//
//			VoteUser vuo = new VoteUser(mat, oui);
//			VoteUser vun = new VoteUser(mat, non);
//
//			voteusersRepo.save(vuo);
//			voteusersRepo.save(vun);
//		}
//
//		usersRepo.save(mat);
	}
}
