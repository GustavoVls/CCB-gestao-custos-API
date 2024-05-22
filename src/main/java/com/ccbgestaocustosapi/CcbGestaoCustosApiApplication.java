package com.ccbgestaocustosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CcbGestaoCustosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcbGestaoCustosApiApplication.class, args);
	}

}
