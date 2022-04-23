package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Poll;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.PollsRepository;
import com.example.demo.repository.UsersRepository;

@SpringBootTest
public class PollTest
{
	@Autowired
	UsersRepository usersRepo;

	@Autowired
	PollsRepository pollsRepo;

	@Test
	public void testParseAnswersStringList()
	{
		Poll testPoll = new Poll();

		testPoll.setAnswersString("oui;non;");
		String answers[] = { "oui", "non" };
		String testAnswers[] = testPoll.getAnswersStringList();
		Assertions.assertArrayEquals(answers, testAnswers);

		testPoll.setAnswersString("oui;non;peu etre;");
		String answers2[] = { "oui", "non", "peu etre" };
		String testAnswers2[] = testPoll.getAnswersStringList();
		Assertions.assertArrayEquals(answers2, testAnswers2);
	}

	@Test
	public void testInsertIntoDb()
	{
		Poll testPoll = new Poll("test poll ?", "my Test Poll", null);
		pollsRepo.save(testPoll);
	}

	@Test
	public void testFindPollById()
	{
		Poll testPoll = new Poll("test poll ?", "my Test Poll", null);
		Poll savedPoll = pollsRepo.save(testPoll);

		Optional<Poll> dbPoll = pollsRepo.findById(savedPoll.getId());
		Assertions.assertEquals(savedPoll.getDescription(), dbPoll.get().getDescription());
		Assertions.assertEquals(savedPoll.getTitle(), dbPoll.get().getTitle());
	}

	@Test
	public void testFindPollsByOwner()
	{
		User user = new User("test1", "name", "mdp", "mdp", Role.READER);
		usersRepo.save(user);
		
		Poll poll1 = new Poll("poll1", "description", user);
		Poll poll2 = new Poll("poll2", "description", user);
		pollsRepo.save(poll1);
		pollsRepo.save(poll2);

		List<Poll> polls = pollsRepo.findAllByOwner(user);
		Assertions.assertEquals(polls.size(), 2);
		Assertions.assertTrue(poll1.getId() == polls.get(0).getId() || poll1.getId() == polls.get(1).getId());
		Assertions.assertTrue(poll2.getId() == polls.get(0).getId() || poll2.getId() == polls.get(1).getId());
	}
	
	@Test
	public void testDeletePollsByOwner()
	{
		User user = new User("test2", "name", "mdp", "mdp", Role.READER);
		usersRepo.save(user);
		
		Poll poll1 = new Poll("poll1", "description", user);
		Poll poll2 = new Poll("poll2", "description", user);
		pollsRepo.save(poll1);
		pollsRepo.save(poll2);

		List<Poll> polls = pollsRepo.findAllByOwner(user);
		Assertions.assertEquals(polls.size(), 2);
		
		pollsRepo.deleteAllByOwner(user);
		
		List<Poll> polls2 = pollsRepo.findAllByOwner(user);
		Assertions.assertEquals(polls2.size(), 0);
	}
}
