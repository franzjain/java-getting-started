package com.franz.max2.parser;

/**
 * Carry any failure happen during People Column Validation execution
 * @author Franz
 *
 */
public class PCValidationFailure extends Exception{

	private static final long serialVersionUID = 1L;

	public PCValidationFailure(String message) {
		super(message);
	}

	public PCValidationFailure(Throwable cause) {
		super(cause);
	}
	
}