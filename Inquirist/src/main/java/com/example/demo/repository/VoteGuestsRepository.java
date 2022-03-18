package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.VoteGuest;

public interface VoteGuestsRepository extends CrudRepository<VoteGuest, Long>
{
}
