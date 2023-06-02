package org.jwick.usermanagementapp.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Time {

	private Time() {
	}
	public static LocalDateTime getCurrentTime() {
		return LocalDateTime.now();
	}
	
	public static LocalDateTime convert(long timestamp) {
		Instant instant = Instant.ofEpochMilli(timestamp);
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}
	
}
