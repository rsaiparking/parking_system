package com.tu.rsai.parking.system.entity;

import org.springframework.stereotype.Component;

@Component
public class Driver {

	private String plateNumber;
	private String email;
	private long parkingTimeInMs;
	
	public long getParkingTimeInMs() {
		return parkingTimeInMs;
	}

	public void setParkingTimeInMs(long parkingTimeInMs) {
		this.parkingTimeInMs = parkingTimeInMs;
	}

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
