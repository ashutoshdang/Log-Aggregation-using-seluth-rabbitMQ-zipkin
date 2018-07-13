package com.asu;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;
import org.springframework.web.bind.annotation.RequestMapping;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinStreamServer
@EnableZipkinServer
//@EnableBinding
public class ZipkinServerTestApplication {

	private static final Logger log = LoggerFactory.getLogger(ZipkinServerTestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ZipkinServerTestApplication.class, args);
	}
	@RabbitListener(queues = "sleuth.sleuth")
	  void processOrder(String body) {
	    log.info("Got message with body [" + body + "]");
	  }
	@RequestMapping("/api") public String printDate() {
	    return new Date().toString();
	  }
}
