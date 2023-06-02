package org.jwick.usermanagementapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.jwick.commonserviceapp.context.UserDetailsContextHolder;
import org.jwick.commonserviceapp.model.DataResponse;
import org.jwick.usermanagementapp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService service;
	
    @GetMapping("/details")
    public ResponseEntity<DataResponse> details(@RequestParam String userName) {
    	
    	
        return ResponseEntity.ok(
        		new DataResponse(true, service.getUserDetails(userName)
        				));
    }
    
    @GetMapping("/myDetails")
    public ResponseEntity<DataResponse> myDetails() {
    	
    	
        return ResponseEntity.ok(
        		new DataResponse(true, UserDetailsContextHolder.getContext()
        				));
    }
    
  }
