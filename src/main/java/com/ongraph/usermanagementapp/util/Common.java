package com.ongraph.usermanagementapp.util;

import jakarta.servlet.http.HttpServletRequest;

public class Common {

	private Common() {
		
	}
	public static String getIpAddress(HttpServletRequest request) {
		String ipAddress=request.getHeader("X-Forwarded-For");
		if(ipAddress==null) {
			ipAddress=request.getRemoteAddr();
		}
		return ipAddress;
	}
}
