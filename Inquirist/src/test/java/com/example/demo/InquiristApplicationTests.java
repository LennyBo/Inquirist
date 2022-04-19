package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class InquiristApplicationTests
{
	@Autowired
	private ApplicationContext context;

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
