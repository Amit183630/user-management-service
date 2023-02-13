package com.ongraph.usermanagementapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ongraph.commonserviceapp.context.UserDetailsContextHolder;
import com.ongraph.commonserviceapp.model.DataResponse;
import com.ongraph.commonserviceapp.model.UserDetails;
import com.ongraph.usermanagementapp.entity.User;
import com.ongraph.usermanagementapp.service.UserService;

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
