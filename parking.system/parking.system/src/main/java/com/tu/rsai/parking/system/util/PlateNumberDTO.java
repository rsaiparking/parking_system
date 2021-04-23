package com.tu.rsai.parking.system.util;

import javax.validation.constraints.NotEmpty;

public class PlateNumberDTO {

	@NotEmpty
	private String plateNumber;

	public String getPlateNumber() {
	    return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
	    this.plateNumber = plateNumber;
	}

}
