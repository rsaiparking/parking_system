package com.tu.rsai.parking.system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "DRIVER")
public class Driver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String plateNumber;
	private String email;
	private long parkingTimeInMs;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
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
