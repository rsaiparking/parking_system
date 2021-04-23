package com.tu.rsai.parking.system.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.tu.rsai.parking.system.services.ParkingService;
import com.tu.rsai.parking.system.util.DriverDTO;
import com.tu.rsai.parking.system.util.PlateNumberDTO;

@RestController
@RequestMapping("/parkings")
public class ParkingController extends GlobalExceptionController {

	@Autowired
	private ParkingService parkingService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public String retrieveParkingsIdentifiers() {
		return new Gson().toJson(parkingService.retrieveParkingIdentifiers());
	}

	@GetMapping(value = "/{parkingId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String retrieveParkingByIdentifier(@PathVariable(name = "parkingId") int parkingId) {
		return new Gson().toJson(parkingService.retrieveParkingById(parkingId));
	}
	
	@PostMapping("/{parking_id}/cells/{cell_id}")
	public ResponseEntity<String> park(@PathVariable(name = "parking_id") int parkingId, @PathVariable (name = "cell_id") int cellId, @Valid @RequestBody DriverDTO driverDTO) {
	   parkingService.parkDriver(parkingId, cellId, driverDTO);

	   return ResponseEntity.status(HttpStatus.OK).body("Driver parked on cell [" + cellId + "] successfully.");
	}

	@DeleteMapping("/{parking_id}/cells/{cell_id}")
	public ResponseEntity<String> unpark(@PathVariable(name = "parking_id") int parkingId, @PathVariable (name = "cell_id") int cellId, @Valid @RequestBody PlateNumberDTO plateNumberDTO) {
	   String plateNumber = plateNumberDTO.getPlateNumber();
	   parkingService.unparkDriver(parkingId, cellId, plateNumber);

	   return ResponseEntity.status(HttpStatus.OK).body("Driver with plate number [" + plateNumber + "] unparked successfully from cell [" + cellId + "].");
	}

}
