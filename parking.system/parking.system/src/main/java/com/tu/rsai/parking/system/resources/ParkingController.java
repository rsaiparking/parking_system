package com.tu.rsai.parking.system.resources;

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
import com.tu.rsai.parking.system.entity.Driver;
import com.tu.rsai.parking.system.entity.PlateNumber;
import com.tu.rsai.parking.system.services.ParkingService;
import com.tu.rsai.parking.system.util.PlateNumberDTO;

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

	@PostMapping(value = "/asd", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String get(@RequestBody PlateNumber plateNumber) {
		return plateNumber.getPlateNumber();
	}
	
	@PostMapping("/{parking_id}/cells/{cell_id}")
	public ResponseEntity<String> park(@PathVariable(name = "parking_id") int parkingId, @PathVariable (name = "cell_id") int cellId, @RequestBody Driver driver) {
	   if(parkingService.isSuccessfullyParked(parkingId,cellId,driver)){
	      return ResponseEntity.status(HttpStatus.OK).body("Driver parked on cell [" + cellId + "] successfully");
	   }

	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong cell number or cell is not free");
	}

	@DeleteMapping("/{parking_id}/cells/{cell_id}")
	public ResponseEntity<String> unpark(@PathVariable(name = "parking_id") int parkingId, @PathVariable (name = "cell_id") int cellId,@RequestBody PlateNumberDTO plateNumber) {
	   String number = plateNumber.getPlateNumber();
	   if(parkingService.isSuccessfullyUnparked(parkingId,cellId,number)){
	      return ResponseEntity.status(HttpStatus.OK).body("Cell [" + cellId + "] freed successfully");
	   }

	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot free the cell successfully");
	}

}
