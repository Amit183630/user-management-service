package org.jwick.usermanagementapp.service;

import org.jwick.usermanagementapp.entity.User;

public interface EmailService {
	
	public void emitEmailRegistrationEvent(User user);
	
	public String customeUrl(String confirmationToken);
}
