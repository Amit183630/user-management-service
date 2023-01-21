package com.ongraph.usermanagementapp.model;

import lombok.Getter;

@Getter
public enum ErrorCodes {

	E_BAD400("Invalid Request body"),
	E_NOTFOUND404("Data not found"),
	E_AUTH401("Authentication failed"),
	E_ISE500("An unexpected error occurred");
	String msg;
	private ErrorCodes(String msg) {
		this.msg=msg;
	}
}
