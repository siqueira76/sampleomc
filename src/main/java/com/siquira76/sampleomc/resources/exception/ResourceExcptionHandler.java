package com.siquira76.sampleomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.siquira76.sampleomc.services.exceptions.DataIntegrityExeption;
import com.siquira76.sampleomc.services.exceptions.ObjectNotFondException;

@ControllerAdvice
public class ResourceExcptionHandler {
	
	@ExceptionHandler(ObjectNotFondException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFondException e, HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityExeption.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityExeption e, HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

}
