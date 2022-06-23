package com.moviesvc.moviesvc.exceptionhandling;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.moviesvc.moviesvc.exceptions.InvalidFieldsException;
import com.moviesvc.moviesvc.exceptions.NotFoundException;



@ControllerAdvice
public class MyControllerAdvice {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorMessage> noCityFoundHandler(NotFoundException NotFoundException) {
		return new ResponseEntity<ErrorMessage>(new ErrorMessage(new Timestamp(System.currentTimeMillis()), 404,
				"Not Found", NotFoundException.getMessage()), HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(java.lang.IllegalArgumentException.class)
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorMessage> handleIllegalArgsException(java.lang.IllegalArgumentException ex) {
		return new ResponseEntity<ErrorMessage>(
				new ErrorMessage(new Timestamp(System.currentTimeMillis()), 400, "Bad Request", ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

//	@Override
//    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
//    		HttpHeaders headers, HttpStatus status, WebRequest request) {
//  
//    	return new ResponseEntity<>(new ErrorMessage(new Timestamp(System.currentTimeMillis()), 405,
//				"No such method found", "hey"), HttpStatus.METHOD_NOT_ALLOWED);
//    }

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorMessage> handleException(HttpRequestMethodNotSupportedException ex) {
		return new ResponseEntity<ErrorMessage>(
				new ErrorMessage(new Timestamp(System.currentTimeMillis()), 405, "Bad Request", ex.getMessage()),
				HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(InvalidFieldsException.class)
	public ResponseEntity<ErrorMessage> invalidFieldHandler(InvalidFieldsException invalidFieldException) {
		return new ResponseEntity<ErrorMessage>(new ErrorMessage(new Timestamp(System.currentTimeMillis()), 400,
				"Bad field value", invalidFieldException.getMessage()), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorMessage> handleMissingArguments(MissingServletRequestParameterException ex) {
		return new ResponseEntity<ErrorMessage>(
				new ErrorMessage(new Timestamp(System.currentTimeMillis()), 400, "Missing value", ex.getMessage()),
				HttpStatus.BAD_REQUEST);

	}

}
