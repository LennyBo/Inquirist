package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String signup(@ModelAttribute(value = "user") User user, Map<String, Object> model)
	{
		model.put("user", new User());
		return "signup";
	}

	@PostMapping("/register")
	public RedirectView register(@ModelAttribute(value = "user") User user, Map<String, Object> model)
	{
		user.encryptPasswords();
		usersRepo.save(user);

		return new RedirectView("/polls");
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout)
	{
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}
}
