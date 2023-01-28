package com.ongraph.usermanagementapp.service;

import com.ongraph.usermanagementapp.dto.AddLoginHistoryDTO;
import com.ongraph.usermanagementapp.entity.UserLoginHistory;

public interface UserLoginHistoryService {

	void emitAddLoginHistoryEvent(AddLoginHistoryDTO addLoginHistoryDTO);
	
	UserLoginHistory addLoginHistory(AddLoginHistoryDTO addLoginHistoryDTO);
}
