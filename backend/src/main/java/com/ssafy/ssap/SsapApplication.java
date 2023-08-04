package com.ssafy.ssap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SsapApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsapApplication.class, args);
	}

}
