package com.digital.telco.utility.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public  class BaseException extends Exception {

	// Type to distinguish exceptions
	private static final long serialVersionUID = Long.MAX_VALUE;
	private String message;
	private ErrorCodes code;
    private HttpStatus httpStatus;

}
