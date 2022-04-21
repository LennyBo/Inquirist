package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Poll;
import com.example.demo.model.User;

public interface PollsRepository extends PagingAndSortingRepository<Poll, Long>
{
	List<Poll> findAllByOwner(User owner);

	@Transactional
	void deleteAllByOwner(User owner);

	Page<Poll> findAllByTitleContainingAndDescriptionContainingAndNbVotesBetween(String title, String description, int start, int end, Pageable pageable);
}
