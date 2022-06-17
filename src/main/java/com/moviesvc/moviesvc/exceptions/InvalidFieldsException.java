package com.moviesvc.moviesvc.exceptions;

public class InvalidFieldsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;

    public InvalidFieldsException(String message) {
      
        this.message=message;
    }

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
