package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Answer;
import com.example.demo.model.Poll;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.VoteUser;
import com.example.demo.repository.AnswersRepository;
import com.example.demo.repository.PollsRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.VoteUsersRepository;

@SpringBootTest
public class VoteUserTest
{
	@Autowired
	UsersRepository usersRepo;
	
	@Autowired
	AnswersRepository answersRepo;

	@Autowired
	VoteUsersRepository voteUsersRepo;

	@Autowired
	PollsRepository pollsRepo;

	@Test
	public void testInsertIntoDb()
	{
		Poll testPoll = new Poll("test poll ?", "my Test Poll", null);
		pollsRepo.save(testPoll);

		Answer answer = new Answer(testPoll, "oui");
		answersRepo.save(answer);
		
		User testUser = new User("user11121", "mat321", "mdp", "mdp", Role.READER);
		usersRepo.save(testUser);
		
		VoteUser newVote = new VoteUser(testUser, answer, pollsRepo);
		voteUsersRepo.save(newVote);
	}
	
	@Test
	public void testNbVote()
	{
		Poll testPoll = new Poll("test pollkk11 ?", "my Test Poll 123", null);
		pollsRepo.save(testPoll);

		Answer answer = new Answer(testPoll, "oui");
		answersRepo.save(answer);

		
		User testUser = new User("user11121AA", "mat3210", "mdp", "mdp", Role.READER);
		usersRepo.save(testUser);

		User testUser2 = new User("user11121AA1", "mat32100", "mdp", "mdp", Role.READER);
		usersRepo.save(testUser2);
		
		VoteUser newVote = new VoteUser(testUser, answer, pollsRepo);
		voteUsersRepo.save(newVote);

		VoteUser newVote2 = new VoteUser(testUser2, answer, pollsRepo);
		voteUsersRepo.save(newVote2);
		
		int nbVote = voteUsersRepo.findByAnswer(answer).size();
		Assertions.assertEquals(nbVote, 2);
	}

	@Test
	public void testFindVoteUsersByUser()
	{
		Poll poll1 = new Poll("test poll ?", "my Test Poll", null);
		pollsRepo.save(poll1);

		Answer answer1 = new Answer(poll1, "oui");
		Answer answer2 = new Answer(poll1, "non");
		answersRepo.save(answer1);
		answersRepo.save(answer2);
		
		User user = new User("user11qDdsS121dqs", "mat321", "mdp", "mdp", Role.READER);
		usersRepo.save(user);
		
		VoteUser vote1 = new VoteUser(user, answer1, pollsRepo);
		VoteUser vote2 = new VoteUser(user, answer2, pollsRepo);
		voteUsersRepo.save(vote1);
		voteUsersRepo.save(vote2);
		
		List<VoteUser> votes = voteUsersRepo.findAllByUser(user);
		Assertions.assertEquals(votes.size(), 2);
		Assertions.assertEquals(vote1.getId(), votes.get(0).getId());
		Assertions.assertEquals(vote2.getId(), votes.get(1).getId());
	}

	@Test
	public void testFindVoteUsersByAnswer()
	{
		Poll poll1 = new Poll("test poll ?", "my Test Poll", null);
		pollsRepo.save(poll1);

		Answer answer1 = new Answer(poll1, "oui");
		answersRepo.save(answer1);
		
		User user = new User("user111sdf21dqs", "mat321", "mdp", "mdp", Role.READER);
		usersRepo.save(user);
		
		VoteUser vote1 = new VoteUser(user, answer1, pollsRepo);
		voteUsersRepo.save(vote1);

		List<VoteUser> votes = voteUsersRepo.findByAnswer(answer1);
		Assertions.assertEquals(votes.size(), 1);
		Assertions.assertEquals(vote1.getId(), votes.get(0).getId());
	}

	@Test
	public void testDeleteVotesUsersByAnswer()
	{
		Poll poll1 = new Poll("test poll ?", "my Test Poll", null);
		Poll poll2 = new Poll("test poll ?", "my Test Poll", null);
		pollsRepo.save(poll1);
		pollsRepo.save(poll2);

		Answer answer1 = new Answer(poll1, "oui");
		answersRepo.save(answer1);
		
		User user = new User("user111dsqfqsdf21dqs", "mat321", "mdp", "mdp", Role.READER);
		usersRepo.save(user);
		
		VoteUser vote1 = new VoteUser(user, answer1, pollsRepo);
		voteUsersRepo.save(vote1);

		List<VoteUser> votes = voteUsersRepo.findAllByUser(user);
		Assertions.assertEquals(votes.size(), 1);

		voteUsersRepo.deleteAllByAnswer(answer1);

		List<VoteUser> votes2 = voteUsersRepo.findAllByUser(user);
		Assertions.assertEquals(votes2.size(), 0);
	}
}
