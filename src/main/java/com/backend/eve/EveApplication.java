package com.backend.eve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@SpringBootApplication

public class EveApplication {

	public static void main(String[] args) {
		SpringApplication.run(EveApplication.class, args);
	}

}
