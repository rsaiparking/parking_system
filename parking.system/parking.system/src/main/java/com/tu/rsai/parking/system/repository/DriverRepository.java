package com.tu.rsai.parking.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tu.rsai.parking.system.entity.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long>{

	public Driver findByPlateNumber(String plateNumber);

}
