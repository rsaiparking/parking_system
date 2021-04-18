package com.tu.rsai.parking.system.util;

import com.tu.rsai.parking.system.entity.Cell;

public class CellDTO {

	private int parkingId;
	private int cellNumber;
	private boolean isFree;
	private boolean isReserved;

	public CellDTO() {
		
	}

	public CellDTO(Cell cell) {
		this.parkingId = cell.getParkingId();
		this.cellNumber = cell.getCellNumber();
		this.isFree = cell.isFree();
		this.isReserved = cell.isReserved();
	}

	public int getParkingId() {
		return parkingId;
	}

	public int getCellNumber() {
		return cellNumber;
	}

	public boolean isFree() {
		return isFree;
	}

	public boolean isReserved() {
		return isReserved;
	}

}
