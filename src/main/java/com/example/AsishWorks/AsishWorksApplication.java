package com.example.AsishWorks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication(exclude= SecurityAutoConfiguration.class)
@SpringBootApplication
public class AsishWorksApplication {
	public static void main(String[] args)
	{
		SpringApplication.run(AsishWorksApplication.class, args);
		System.out.println("SERVER STARTED");
	}
}
