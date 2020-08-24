package com.example.JavaAPIYouTube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"controllers, services"})
public class JavaApiYouTubeApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaApiYouTubeApplication.class, args);

	}

}
