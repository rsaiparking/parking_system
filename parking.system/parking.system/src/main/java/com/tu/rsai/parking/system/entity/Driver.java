package com.tu.rsai.parking.system.entity;

import org.springframework.stereotype.Component;

@Component
public class Driver {

	private String plateNumber;
	private String email;

	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
