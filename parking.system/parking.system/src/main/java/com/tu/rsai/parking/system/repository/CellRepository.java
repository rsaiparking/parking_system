package com.tu.rsai.parking.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tu.rsai.parking.system.entity.Cell;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {
	
}
