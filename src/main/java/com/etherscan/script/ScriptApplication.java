package com.etherscan.script;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScriptApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScriptApplication.class, args);
	}

}