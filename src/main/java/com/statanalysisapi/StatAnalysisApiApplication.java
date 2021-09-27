package com.statanalysisapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class StatAnalysisApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(StatAnalysisApiApplication.class, args);
	}
}
