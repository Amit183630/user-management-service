package org.jwick.usermanagementapp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddLoginHistoryDTO {

	String username;
	LocalDateTime loggedInTime;
	boolean loggedIn;
	String ip;
	
}
