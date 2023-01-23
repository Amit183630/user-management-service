package com.ongraph.usermanagementapp.model;

import lombok.Data;

@Data
public class DataResponse {
	
	private boolean success=false;
	private Object data=null;
	private ErrorDetails error=null;
	
	public DataResponse(boolean success,Object data) {
		this.success=success;
		this.data=data;
	}
	
	public DataResponse(ErrorDetails errorDetails) {
		this.error=errorDetails;
	}
}
