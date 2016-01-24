package com.hong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VelocityApplication {
	public static void main(String[] args) {
        Logger logger = LogManager.getLogger(VelocityApplication.class);
		SpringApplication.run(VelocityApplication.class, args);
		logger.info("Test Log4j2 info");
		logger.warn("Test Log4j2 warn");
		logger.error("Test log4j2 error");
	}
}
