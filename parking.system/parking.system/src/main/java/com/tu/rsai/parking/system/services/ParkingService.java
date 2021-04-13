package com.tu.rsai.parking.system.services;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.tu.rsai.parking.system.entity.Cell;
import com.tu.rsai.parking.system.entity.Parking;

@Service
public class ParkingService {

	private static Map<Integer, Parking> parkings;

	public static void initializeParkings() {
		Map<Integer, Parking> currentParkings = new TreeMap<Integer, Parking>();

		int parkingCounter = 1;
		int cellCounter = 1;
		
		while(parkingCounter <= 3) {
			Parking parking = new Parking();

			Cell[][] cells = parking.getCells();

			for (int row = 0; row < 6; row++) {
				for (int col = 0; col < 6; col++) {
					if (row == 0) {
						cells[row][col].setCellNumber(cellCounter);
						cells[row][col].setIsFree(true);

						cellCounter++;
					} else if (row > 0 && (col == 0 || col == 5)) {
						cells[row][col].setCellNumber(cellCounter);
						cells[row][col].setIsFree(true);

						cellCounter++;
					} else if ((row >= 2 && row <= 4) && (col == 2 || col == 3)) {
						cells[row][col].setCellNumber(cellCounter);
						cells[row][col].setIsFree(true);

						cellCounter++;
					}
				}
			}

			currentParkings.put(parkingCounter, parking);

			parkingCounter++;
			cellCounter = 1;
		}

		parkings = currentParkings;
	}

	public Parking retrieveParkingByIdentifier(int identifier) {
		return parkings.get(identifier);
	}

	public Set<Integer> retrieveParkingIdentifiers() {
		return parkings.keySet();
	}
}
