package com.ongraph.usermanagementapp.context;

import org.springframework.stereotype.Component;

import com.ongraph.commonserviceapp.model.UserDetails;

@Component
public class UserDetailsContextHolder {

	private static ThreadLocal<UserDetails> userDetailsContext=new InheritableThreadLocal<>();
	
	public static UserDetails getContext() {
		return userDetailsContext.get();
		
	}
	public static void clear() {
		userDetailsContext.remove();
	}
	public static void set(UserDetails userDetails) {
		userDetailsContext.set(userDetails);
	}
	
	private UserDetailsContextHolder() {
		
	}
}
