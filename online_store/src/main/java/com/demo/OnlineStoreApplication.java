package com.demo;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreApplication.class, args);
		System.out.println("welcome to Online store");
		LocalDateTime date=LocalDateTime.now();
		System.out.println(date);
	}

}
