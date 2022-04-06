package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Answer;
import com.example.demo.model.Poll;
import com.example.demo.model.User;
import com.example.demo.model.VoteUser;

public interface VoteUsersRepository extends CrudRepository<VoteUser, Long>
{
	List<VoteUser> findAllByUser(User user);

	List<VoteUser> findAllByPoll(Poll poll);

	@Transactional
	void deleteAllByAnswer(Answer answer);
}
