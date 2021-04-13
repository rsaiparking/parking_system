package com.tu.rsai.parking.system.resources;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tu.rsai.parking.system.entity.IdentifierDTO;
import com.tu.rsai.parking.system.services.ParkingService;

@RestController
public class ParkingController {

	@Autowired
	private ParkingService parkingService;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public Set<IdentifierDTO> retrieveParkingsIdentifiers() {
		
		return parkingService.retrieveParkingIdentifiers().stream().map(x -> new IdentifierDTO(x)).sorted().collect(Collectors.toSet());
	}
}
