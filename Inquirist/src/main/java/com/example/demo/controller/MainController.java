package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController
{
	@GetMapping
	public String index()
	{
		return "index";
	}

	@GetMapping("/error")
	public String error()
	{
		return "error";
	}
}
