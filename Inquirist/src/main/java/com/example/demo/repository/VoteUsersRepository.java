package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.VoteUser;

public interface VoteUsersRepository extends CrudRepository<VoteUser, Long>
{
}
