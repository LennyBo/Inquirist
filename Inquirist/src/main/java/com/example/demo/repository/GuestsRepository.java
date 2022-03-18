package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Guest;

public interface GuestsRepository extends CrudRepository<Guest, Long>
{
}
