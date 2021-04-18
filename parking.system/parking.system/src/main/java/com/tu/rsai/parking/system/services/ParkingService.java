package com.tu.rsai.parking.system.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tu.rsai.parking.system.entity.Cell;
import com.tu.rsai.parking.system.entity.Driver;
import com.tu.rsai.parking.system.entity.Parking;
import com.tu.rsai.parking.system.repository.CellRepository;
import com.tu.rsai.parking.system.util.CellDTO;
import com.tu.rsai.parking.system.util.ParkingDTO;
import com.tu.rsai.parking.system.util.Payment;

@Service
public class ParkingService {

	private static Map<Integer, Parking> parkings;

	@Autowired
	private MailService mailService;

	@Autowired
	private CellRepository repository;

	@PostConstruct
	public void initializeParkings() {
		Map<Integer, Parking> currentParkings = new TreeMap<Integer, Parking>();

		int parkingCounter = 1;
		int cellCounter = 1;

		while (parkingCounter <= 3) {
			Parking parking = new Parking();
			Cell[][] cells = parking.getCells();

			for (int row = 0; row < 6; row++) {
				for (int col = 0; col < 6; col++) {
					Cell cell = cells[row][col];
					if (row == 0) {
						constructCell(cell, parkingCounter, cellCounter);
						cellCounter++;
					} else if (row > 0 && (col == 0 || col == 5)) {
						constructCell(cell, parkingCounter, cellCounter);
						cellCounter++;
					} else if ((row >= 2 && row <= 4) && (col == 2 || col == 3)) {
						constructCell(cell, parkingCounter, cellCounter);
						cellCounter++;
					}
				}
			}

			currentParkings.put(parkingCounter, parking);

			parkingCounter++;

			cellCounter = 1;
		}

		parkings = currentParkings;

		loadLastStateOfParkings();
	}

	private void loadLastStateOfParkings() {
		List<Cell> takenCells = repository.findAll();
		
		for (Cell cell : takenCells) {
			int cellNumber = cell.getCellNumber();
			int parkingId = cell.getParkingId();

			Parking parking = parkings.get(parkingId);
			Cell currentCell = parking.getCellByCellNumber(cellNumber);
			if (currentCell != null) {
				parking.setCellByCellNumber(cellNumber, cell);
			}
		}
	}

	public static void constructCell(Cell cell, int parkingCounter, int cellCounter) {
		cell.setCellNumber(cellCounter);
		cell.setIsFree(true);
		cell.setParkingId(parkingCounter);
	}

	public ParkingDTO retrieveParkingById(int identifier) {
		ParkingDTO parkingDTO = new ParkingDTO();
		Parking parking = parkings.get(identifier);

		Cell[][] cells = parking.getCells();
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 6; col++) {
				Cell cell = cells[row][col];

				CellDTO cellDTO = new CellDTO(cell);

				parkingDTO.setCell(row, col, cellDTO);
			}
		}

		return parkingDTO;
	}

	public Set<Integer> retrieveParkingIdentifiers() {
		return parkings.keySet();
	}

	@Transactional
	public boolean isSuccessfullyParked(int parkingId, int cellId, Driver driver) {
		Parking parking = parkings.get(parkingId);
		Cell[][] cells = parking.getCells();

		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 6; col++) {
				Cell cell = cells[row][col];
				if (cell.getCellNumber() == cellId) {
					if (cell.isFree()) {
						cell.setIsFree(false);
						driver.setParkingTimeInMs(System.currentTimeMillis());
						cell.setDriver(driver);

						repository.save(cell);

						return true;
					} else {
						return false;
					}
				}
			}
		}

		return false;
	}

	public boolean isSuccessfullyUnparked(int parkingId, int cellId, String plateNumber) {
		Parking parking = parkings.get(parkingId);
		Cell[][] cells = parking.getCells();

		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 6; col++) {
				Cell cell = cells[row][col];
				if (cell.getCellNumber() == cellId) {
					if (!cell.isFree() && cell.getDriver().getPlateNumber().equals(plateNumber)) {
						double price = calculatePrice(cell.getDriver(), cell.isReserved());
						mailService.sendMail("konstantinivanov09@yahoo.com", price);
						cell.setIsFree(true);

						repository.delete(cell);

						cell.setDriver(null);

						return true;
					} else {
						return false;
					}
				}
			}
		}

		return false;
	}

	private double calculatePrice(Driver driver, boolean isReserved) {
		double price = Math.ceil(((System.currentTimeMillis() - driver.getParkingTimeInMs()) / 60000.0)) * Payment.NORMAL_PRICE;
		if (isReserved) {
			price = Math.ceil(((System.currentTimeMillis() - driver.getParkingTimeInMs()) / 60000.0)) * Payment.SPECIAL_PRICE;
		}

		return price;
	}
}
