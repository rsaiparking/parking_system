package com.tu.rsai.parking.system.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tu.rsai.parking.system.entity.Cell;
import com.tu.rsai.parking.system.entity.Parking;

@Service
public class ParkingService {

	private static Map<Integer, Parking> parkings;

	public ParkingService() {
		parkings = initializeParkings();
	}

	private static Map<Integer, Parking> initializeParkings() {
		Map<Integer, Parking> parkings = new HashMap<Integer, Parking>();

		int parkingCounter = 1;
		int cellCounter = 1;
		
		while(parkingCounter <= 3) {
			Parking parking = new Parking();

			Cell[][] cells = parking.getCells();

			for (int row = 0; row < 6; row++) {
				for (int col = 0; col < 6; col++) {
					if (row == 0) {
						Cell cell = new Cell();
						cells[row][col] = cell;
						cell.setCellNumber(cellCounter);

						cellCounter++;
					} else if (row > 0 && (col == 0 || col == 5)) {
						Cell cell = new Cell();
						cells[row][col] = cell;
						cell.setCellNumber(cellCounter);

						cellCounter++;
					} else if ((row >= 2 && row <= 4) && (col == 2 || col == 3)) {
						Cell cell = new Cell();
						cells[row][col] = cell;
						cell.setCellNumber(cellCounter);

						cellCounter++;
					}
				}
			}

			parkings.put(parkingCounter, parking);

			parkingCounter++;
			cellCounter = 1;
		}

		return parkings;
	}

	public Parking retrieveParkingByIdentifier(int identifier) {
		return parkings.get(identifier);
	}

	public Set<Integer> retrieveParkingIdentifiers() {
		return parkings.keySet();
	}
}
