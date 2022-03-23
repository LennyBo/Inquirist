package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Poll;

public interface PollsRepository extends CrudRepository<Poll, Long>
{
}
