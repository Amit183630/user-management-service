package org.jwick.usermanagementapp.controller;

import org.jwick.usermanagementapp.dto.LoginRequest;
import org.jwick.usermanagementapp.dto.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.jwick.commonserviceapp.model.DataResponse;
import org.jwick.usermanagementapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<DataResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {

		return ResponseEntity.ok(
				new DataResponse(true,
						userService.addUser(signupRequest)
						)
				);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<DataResponse> signin(@Valid @RequestBody LoginRequest loginRequest) {

		return ResponseEntity.ok(
				new DataResponse(true,
						userService.loginUser(loginRequest)
						)
				);
	}
	
	 @GetMapping("/confirmEmail")
	    public ResponseEntity<DataResponse> confirmEmail(@RequestParam String confirmationToken){
	         userService.accountConfirmation(confirmationToken);
	    	
	        return ResponseEntity.ok(
	        		new DataResponse(true, "Thank you! You have activated your account successfully")
	        		);
	    	
	    }
}
