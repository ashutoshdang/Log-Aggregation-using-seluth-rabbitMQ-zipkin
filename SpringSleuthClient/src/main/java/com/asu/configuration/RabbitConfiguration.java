/*package com.asu.configuration;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfiguration {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	RabbitTemplate rabbitTemplate;

	@RequestMapping("/")
	public String callBackend() {
		return restTemplate.getForObject("http://localhost:9000/api",
				String.class);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@RequestMapping("/message")
	public String message() {
		this.rabbitTemplate.convertAndSend("sleuth-webmvc-example", "body");
		return "Sent the message to Rabbit. Let's wait for Backend to get it.";
	}

	public static void main(String[] args) {
		SpringApplication.run(RabbitConfiguration.class,
				"--spring.application.name=frontend", "--server.port=8081");
	}

	@Autowired
	AmqpAdmin amqpAdmin;

	@PostConstruct
	void setup() {
		amqpAdmin.declareQueue(new Queue("sleuth-webmvc-example"));
	}
	
}*/