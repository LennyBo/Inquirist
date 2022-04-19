package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.User;
import com.example.demo.repository.UsersRepository;

@Controller
@RequestMapping("/")
public class AuthenticationController
{
	@Autowired
	UsersRepository usersRepo;

	@GetMapping("/signup")
	public String create(@ModelAttribute(value = "user") User user, Map<String, Object> model)
	{
		model.put("user", new User());
		return "user_create";
	}

	@PostMapping("/insert")
	public RedirectView insert(@ModelAttribute(value = "user") User user, Map<String, Object> model)
	{
		user.encryptPasswords();
		usersRepo.save(user);

		return new RedirectView("/polls");
	}
}
