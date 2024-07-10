package com.ccbgestaocustosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class CcbGestaoCustosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcbGestaoCustosApiApplication.class, args);
	}

}
