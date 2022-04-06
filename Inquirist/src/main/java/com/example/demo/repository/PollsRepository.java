package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Poll;
import com.example.demo.model.User;

public interface PollsRepository extends CrudRepository<Poll, Long>
{
	List<Poll> findAllByOwner(User owner);
}
