package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	
	/*@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	  return new BCryptPasswordEncoder();
	}*/


	@PostConstruct
	public void init()
	{
<<<<<<< HEAD
		PasswordEncoder encoder = new Encoder().passwordEncoder();
		for (int i = 0; i < 10; i++)
		{
			Category c = new Category("Categorie" + i);
			categoriesRepo.save(c);
			Person p = new Person("Chevre" + i, "Sebastien" + i, c);
			personsRepo.save(p);
		}

		User mat = new User("matthieu", "mat", encoder.encode("password"), true);
=======
		User mat = new User("user", "mat", "mdp", true);
>>>>>>> 07d7729cdeb36ba53eb779925b40185abd0ca8d8
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
