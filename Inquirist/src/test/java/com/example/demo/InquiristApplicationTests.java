package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.MainController;
import com.example.demo.controller.PollController;

@SpringBootTest
class InquiristApplicationTests {

	
	
	@Test
	void contextLoads() {
		//assertThat(pollController).isNotNull();
	}
	
	
	
	@Test
	public void mainShouldContainHome() throws Exception{
		//assertThat(this.restTemplate.getForObject("http://localhost:"+String.class, null)).)
	}

}
