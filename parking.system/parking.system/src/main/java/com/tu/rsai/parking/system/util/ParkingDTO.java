package com.tu.rsai.parking.system.util;

public class ParkingDTO {

	private CellDTO[][] cells;

	public ParkingDTO() {
		this.cells = new CellDTO[6][6];

		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 6; col++) {
				this.cells[row][col] = new CellDTO();
			}
		}
	}

	public CellDTO[][] getCells() {
		return this.cells;
	}

	public void setCell(int row, int col, CellDTO cell) {
		this.cells[row][col] = cell;
	}
}
