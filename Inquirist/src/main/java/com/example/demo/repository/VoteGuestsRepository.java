package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Answer;
import com.example.demo.model.VoteGuest;

public interface VoteGuestsRepository extends CrudRepository<VoteGuest, Long>
{
	@Transactional
	void deleteAllByAnswer(Answer answer);
}
