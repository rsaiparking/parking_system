package com.tu.rsai.parking.system.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.tu.rsai.parking.system.services.ParkingService;

@RestController
@RequestMapping("/parkings")
public class ParkingController {

	@Autowired
	private ParkingService parkingService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public String retrieveParkingsIdentifiers() {
		return new Gson().toJson(parkingService.retrieveParkingIdentifiers());
	}

	@GetMapping(value = "/{identifier}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String retrieveParkingByIdentifier(@PathVariable(name = "identifier") int identifier) {
		return new Gson().toJson(parkingService.retrieveParkingByIdentifier(identifier));
	}
}
