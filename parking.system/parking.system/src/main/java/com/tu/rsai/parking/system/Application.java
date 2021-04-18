package com.tu.rsai.parking.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tu.rsai.parking.system.services.ParkingService;

@SpringBootApplication
public class Application {

	@Autowired
	private ParkingService service;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
