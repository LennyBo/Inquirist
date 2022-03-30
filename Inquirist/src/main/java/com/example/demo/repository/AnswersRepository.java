package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Answer;


public interface AnswersRepository extends CrudRepository<Answer, Long>
{
	List<Answer> findByPollId(long pollId);
}