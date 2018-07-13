package com.asu;

import javax.annotation.PostConstruct;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class SpringSleuthClientApplication {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RabbitTemplate rabbitTemplate;
	@Autowired
	AmqpAdmin amqpAdmin;
	private static final Logger LOG = Logger.getLogger(SpringSleuthClientApplication.class.getName());

	@RequestMapping("/message")
	public String message() {
		this.rabbitTemplate.convertAndSend("sleuth.sleuth", "body");
		LOG.info("sending messages from cloud sleuth");
		return "Sent the message to Rabbit. Let's wait for Backend to get it.";
	}

	

	public static void main(String[] args) {
		SpringApplication.run(SpringSleuthClientApplication.class, args);
	}

	@RequestMapping("/")
	public String callBackend() {
		return restTemplate.getForObject("http://localhost:9000/api",
				String.class);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/*@RequestMapping("/")
	public String rest1() {
		return "I'm SleuthClient";
	}*/

	@RequestMapping("/test1")
	public String test1() {
		return "Returning from test1 rest endpoint";
	}

	@RequestMapping("/hello")
	public String hello() {
		LOG.log(Level.INFO, "you called hello");
		/*
		 * return restTemplate.getForObject("http://localhost:8080",
		 * String.class);
		 */
		return "hello there";
	}

	@PostConstruct
	void setup() {
		amqpAdmin.declareQueue(new Queue("sleuth-webmvc-example"));
	}

}
