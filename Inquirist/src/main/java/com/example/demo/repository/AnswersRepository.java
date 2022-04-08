package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Answer;
import com.example.demo.model.Poll;

public interface AnswersRepository extends CrudRepository<Answer, Long>
{
	List<Answer> findAllByPollId(long pollId);

	List<Answer> findAllByPoll(Poll poll);

	@Transactional
	void deleteAllByPoll(Poll poll);
}