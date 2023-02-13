package com.ongraph.usermanagementapp.service;

import com.ongraph.usermanagementapp.entity.User;

public interface EmailService {
	
	public void emitEmailRegistrationEvent(User user);
	
	public String customeUrl(String confirmationToken);
}
