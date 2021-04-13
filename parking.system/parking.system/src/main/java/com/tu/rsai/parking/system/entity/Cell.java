package com.tu.rsai.parking.system.entity;

import org.springframework.stereotype.Component;

@Component
public class Cell {

	private int cellNumber;
	private boolean isFree;
	private boolean isReserved;
	private Driver driver;

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public int getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(int cellNumber) {
		this.cellNumber = cellNumber;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setIsFree(boolean isFree) {
		this.isFree = isFree;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setIsReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}
	
}
