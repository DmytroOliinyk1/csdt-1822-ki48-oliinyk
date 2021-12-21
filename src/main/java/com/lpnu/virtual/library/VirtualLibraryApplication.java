package com.lpnu.virtual.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VirtualLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualLibraryApplication.class, args);
	}

}
