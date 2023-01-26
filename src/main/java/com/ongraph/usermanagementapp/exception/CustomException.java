package com.ongraph.usermanagementapp.exception;

import com.ongraph.usermanagementapp.model.ErrorCodes;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final ErrorCodes errorCode;
	private final String message;
	
	public CustomException(ErrorCodes errorCode) {
		super(errorCode.getMsg());
		this.errorCode=errorCode;
		this.message=errorCode.getMsg();
	}
	
	public CustomException(ErrorCodes errorCode,String message) {
		super(message);
		this.errorCode=errorCode;
		this.message=message;
	}
}
