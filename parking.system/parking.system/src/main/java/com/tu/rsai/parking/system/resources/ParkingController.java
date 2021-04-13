package com.tu.rsai.parking.system.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingController {

	@GetMapping("/")
	public String asd() {
		return "asd";
	}
}
