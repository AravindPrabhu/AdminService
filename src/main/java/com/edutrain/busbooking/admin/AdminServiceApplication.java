package com.edutrain.busbooking.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.edutrain.busroute.admin.repository.BusRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses=BusRepository.class)
@EnableEurekaClient
@ComponentScan(basePackages="com.edutrain.busroute.admin.controller,com.edutrain.busroute.admin.model,com.edutrain.busroute.admin.repository")
public class AdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminServiceApplication.class, args);
	}

}
