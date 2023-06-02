package org.jwick.usermanagementapp.transformer;

import org.jwick.usermanagementapp.entity.User;
import org.jwick.usermanagementapp.entity.UserLoginHistory;
import org.jwick.commonserviceapp.model.UserDetails;

public class ModelTransformer {

	private ModelTransformer() {
		
	}
	
	public static UserDetails convertToUserDetails(User user){
		var userDetails= UserDetails.builder()
								.email(user.getEmail())
								.enabled(user.isEnabled())
								.firstName(user.getFirstName())
								.lastName(user.getLastName())
								.id(user.getId())
								.password(user.getPassword())
								.phoneNo(user.getPhoneNo())
								.userName(user.getUserName())
								.build();
		user.getLoginHistories().stream().forEach(loginHistory ->
			userDetails.getLoginHistoryList().add(convertToUserLoginHistoryDetails(loginHistory))
			);
		user.getRoles().stream().forEach(role->
			userDetails.getRoles().add(role.getUserRole())
			);
		return userDetails;
	}
	
	public static UserDetails.UserLoginHistoryDetails convertToUserLoginHistoryDetails(UserLoginHistory loginHistory) {
		return UserDetails.UserLoginHistoryDetails.builder()
				.ipAddress(loginHistory.getIpAddress())
				.loggedIn(loginHistory.isLoggedIn())
				.logInAttemptTime(loginHistory.getLogInAttemptTime())
				.build();
	}
}
