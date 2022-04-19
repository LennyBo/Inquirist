package com.example.demo;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UsersRepository;

@SpringBootTest
public class UserTest
{
	@Autowired
	UsersRepository usersRepo;

	@Test
	public void testInsertIntoDb()
	{
		User testUser = new User("user11", "mat33", "mdp", "mdp", Role.READER);
		usersRepo.save(testUser);
	}

	@Test
	public void testFindById()
	{
		User testUser = new User("user111", "mat32", "mdp", "mdp", Role.READER);
		long id = usersRepo.save(testUser).getId();

		Optional<User> dbUser = usersRepo.findById(id);
		Assertions.assertEquals(testUser.getName(), dbUser.get().getName());
		Assertions.assertEquals(testUser.getUsername(), dbUser.get().getUsername());
	}

	@Test
	public void testFindUserByUserame()
	{
		User testUser = new User("user112", "mat133", "mdp", "mdp", Role.READER);
		User dbUser = usersRepo.save(testUser);
		User findUser = usersRepo.findByUsername("user112");

		Assertions.assertEquals(findUser.getId(), dbUser.getId());
		Assertions.assertEquals(findUser.getUsername(), dbUser.getUsername());
		Assertions.assertEquals(findUser.getName(), dbUser.getName());
	}
}
