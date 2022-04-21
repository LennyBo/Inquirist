package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.model.User;

public interface UsersRepository extends PagingAndSortingRepository<User, Long>
{
	User findByUsername(String username);
	List<User> findAllByUsernameContaining(String username);
}
