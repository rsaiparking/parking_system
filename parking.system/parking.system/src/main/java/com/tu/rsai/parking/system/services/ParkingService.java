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
import com.tu.rsai.parking.system.exceptions.BadRequestException;
import com.tu.rsai.parking.system.repository.CellRepository;
import com.tu.rsai.parking.system.repository.DriverRepository;
import com.tu.rsai.parking.system.util.CellDTO;
import com.tu.rsai.parking.system.util.DriverDTO;
import com.tu.rsai.parking.system.util.ParkingDTO;
import com.tu.rsai.parking.system.util.Payment;

@Service
public class ParkingService {

	private static Map<Integer, Parking> parkings;

	@Autowired
	private MailService mailService;

	@Autowired
	private CellRepository cellRepository;

	@Autowired
	private DriverRepository driverRepository;

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
		List<Cell> takenCells = cellRepository.findAll();
		
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

		if (parking == null) {
			throw new BadRequestException("Wrong parking identifier.");
		}

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
	public void parkDriver(int parkingId, int cellId, DriverDTO driverDTO) {
		Driver driver = createDriverFrom(driverDTO);

		Driver parkedDriver = driverRepository.findByPlateNumber(driver.getPlateNumber());
		if (parkedDriver != null) {
			throw new BadRequestException("Cannot park driver with the same plate number more than once.");
		}

		Parking parking = parkings.get(parkingId);

		if (parking == null) {
			throw new BadRequestException("Wrong parking identifier.");
		}

		Cell[][] cells = parking.getCells();

		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 6; col++) {
				Cell cell = cells[row][col];
				if (cell.getCellNumber() == cellId) {
					if (cell.isFree()) {
						cell.setIsFree(false);
						driver.setParkingTimeInMs(System.currentTimeMillis());
						cell.setDriver(driver);

						cellRepository.save(cell);

						return;
					} else {
						throw new BadRequestException("Cell in not free.");
					}
				}
			}
		}

		throw new BadRequestException("Wrong cell identifier.");
	}

	private Driver createDriverFrom(DriverDTO driverDTO) {
		Driver driver = new Driver();
		driver.setPlateNumber(driverDTO.getPlateNumber());
		driver.setEmail(driverDTO.getEmail());

		return driver;
	}

	public void unparkDriver(int parkingId, int cellId, String plateNumber) {
		Parking parking = parkings.get(parkingId);

		if (parking == null) {
			throw new BadRequestException("Wrong parking identifier.");
		}

		Cell[][] cells = parking.getCells();

		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 6; col++) {
				Cell cell = cells[row][col];
				if (cell.getCellNumber() == cellId) {
					if (!cell.isFree()) {
						if (cell.getDriver().getPlateNumber().equals(plateNumber)) {
							double price = calculatePrice(cell.getDriver(), cell.isReserved());
							mailService.sendMail("konstantinivanov09@yahoo.com", price);

							cell.setIsFree(true);

							cellRepository.delete(cell);

							cell.setDriver(null);

							return;
						} else {
							throw new BadRequestException("Cannot unpark driver. Wrong plate number.");
						}
					}
				}
			}
		}

		throw new BadRequestException("Wrong cell identifier.");
	}

	private double calculatePrice(Driver driver, boolean isReserved) {
		double price = Math.ceil(((System.currentTimeMillis() - driver.getParkingTimeInMs()) / 60000.0)) * Payment.NORMAL_PRICE;
		if (isReserved) {
			price = Math.ceil(((System.currentTimeMillis() - driver.getParkingTimeInMs()) / 60000.0)) * Payment.SPECIAL_PRICE;
		}

		return price;
	}
}
