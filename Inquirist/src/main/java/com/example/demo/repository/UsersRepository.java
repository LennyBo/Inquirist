package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.model.User;

public interface UsersRepository extends PagingAndSortingRepository<User, Long>
{
	User findByUsername(String username);

	Page<User> findAllByUsernameContaining(String username, Pageable pageable);
}
