package com.tu.rsai.parking.system.entity;

import org.springframework.stereotype.Component;

@Component
public class Parking {

	private Cell[][] cells;

	public Parking() {
		cells = new Cell[6][6];

		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 6; col++) {
				cells[row][col] = new Cell();
			}
		}
		
		cells[0][1].setIsReserved(true);
		cells[0][2].setIsReserved(true);
		cells[0][3].setIsReserved(true);
		cells[0][4].setIsReserved(true);
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

}
