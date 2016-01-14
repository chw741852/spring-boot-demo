package com.hong;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VelocityApplication {

	public static void main(String[] args) {
//		SpringApplication.run(VelocityApplication.class, args);
        SpringApplication app = new SpringApplication(VelocityApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
	}
}
