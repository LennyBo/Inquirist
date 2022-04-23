package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Answer;
import com.example.demo.model.Poll;
import com.example.demo.repository.AnswersRepository;
import com.example.demo.repository.PollsRepository;

@SpringBootTest
public class AnswerTest
{
	@Autowired
	AnswersRepository answersRepo;

	@Autowired
	PollsRepository pollsRepo;

	@Test
	public void testInsertIntoDb()
	{
		Answer answer = new Answer(null, "oui");
		answersRepo.save(answer);
	}

	@Test
	public void testFindById()
	{
		Answer answer = new Answer(null, "oui");
		long id = answersRepo.save(answer).getId();

		Optional<Answer> dbUser = answersRepo.findById(id);
		Assertions.assertEquals(answer.getAnswer(), dbUser.get().getAnswer());
	}

	@Test
	public void testFindAnswersByPoll()
	{
		Poll poll = new Poll("title", "description", null);
		pollsRepo.save(poll);
		
		Answer oui = new Answer(poll, "oui");
		Answer non = new Answer(poll, "non");
		answersRepo.save(oui);
		answersRepo.save(non);
		
		List<Answer> answers = answersRepo.findAllByPoll(poll);
		Assertions.assertEquals(answers.size(), 2);
		Assertions.assertTrue(oui.getId() == answers.get(0).getId() || oui.getId() == answers.get(1).getId());
		Assertions.assertTrue(non.getId() == answers.get(0).getId() || non.getId() == answers.get(1).getId());
	}
	
	@Test
	public void testFindAnswersByPollId()
	{
		Poll poll = new Poll("title", "description", null);
		pollsRepo.save(poll);
		
		Answer oui = new Answer(poll, "oui");
		Answer non = new Answer(poll, "non");
		answersRepo.save(oui);
		answersRepo.save(non);
		
		List<Answer> answers = answersRepo.findAllByPollId(poll.getId());
		Assertions.assertEquals(answers.size(), 2);
		Assertions.assertTrue(oui.getId() == answers.get(0).getId() || oui.getId() == answers.get(1).getId());
		Assertions.assertTrue(non.getId() == answers.get(0).getId() || non.getId() == answers.get(1).getId());
	}
	
	@Test
	public void testDeleteAnswersByPoll()
	{
		Poll poll = new Poll("title", "description", null);
		pollsRepo.save(poll);
		
		Answer oui = new Answer(poll, "oui");
		Answer non = new Answer(poll, "non");
		answersRepo.save(oui);
		answersRepo.save(non);
		
		List<Answer> answers = answersRepo.findAllByPoll(poll);
		Assertions.assertEquals(answers.size(), 2);

		answersRepo.deleteAllByPoll(poll);
		
		List<Answer> answers2 = answersRepo.findAllByPoll(poll);
		Assertions.assertEquals(answers2.size(), 0);
	}
}
