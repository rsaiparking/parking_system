package com.tu.rsai.parking.system.util;

import javax.validation.constraints.NotEmpty;

public class DriverDTO {

	@NotEmpty
	private String plateNumber;

	@NotEmpty
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
