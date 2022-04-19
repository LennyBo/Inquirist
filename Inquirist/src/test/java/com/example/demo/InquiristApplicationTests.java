package com.example.demo;

<<<<<<< HEAD
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
=======
>>>>>>> roles
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.MainController;
import com.example.demo.controller.PollController;
import com.example.demo.model.Poll;

@SpringBootTest
class InquiristApplicationTests {

	  @Autowired
	  private ApplicationContext context;

=======

@SpringBootTest
class InquiristApplicationTests
{
>>>>>>> roles
	@Test
	void contextLoads()
	{
		// assertThat(pollController).isNotNull();
	}

	@Test
	public void mainShouldContainHome() throws Exception
	{
		// assertThat(this.restTemplate.getForObject("http://localhost:"+String.class, null)).)
	}

}
