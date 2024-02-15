package com.honeyapple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HoneyappleApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoneyappleApplication.class, args);
	}

}
