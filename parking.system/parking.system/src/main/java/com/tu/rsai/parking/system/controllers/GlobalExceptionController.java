package com.tu.rsai.parking.system.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tu.rsai.parking.system.exceptions.BadRequestException;
import com.tu.rsai.parking.system.util.ErrorDTO;

public class GlobalExceptionController {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleBadRequestException(BadRequestException exception) {
		return new ErrorDTO(exception.getMessage());
	}
}
