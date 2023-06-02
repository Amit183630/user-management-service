package org.jwick.usermanagementapp.service;

import org.jwick.usermanagementapp.dto.AddLoginHistoryDTO;
import org.jwick.usermanagementapp.entity.UserLoginHistory;

public interface UserLoginHistoryService {

	void emitAddLoginHistoryEvent(AddLoginHistoryDTO addLoginHistoryDTO);
	
	UserLoginHistory addLoginHistory(AddLoginHistoryDTO addLoginHistoryDTO);
}
