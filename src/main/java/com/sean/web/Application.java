package com.sean.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
/**
 * @author Sean
 *
 * The main entry point to start the application.
 */
public class Application {

	/**
	 * The main method to start the application.
	 * @param args The arguments of the program.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
