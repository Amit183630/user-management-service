package com.ongraph.usermanagementapp.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.ongraph.usermanagementapp.dto.SignupRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String firstName;
	
	private String lastName;
	
	private String userName;
	
	private String email;
	
	private String password;
	
	private String phoneNo;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
	private Set<Role> roles=new HashSet<>();
	
	public User(SignupRequest signupRequest) {
		this.firstName=signupRequest.getFirstName();
		this.lastName=signupRequest.getLastName();
		this.userName=signupRequest.getUserName();
		this.email=signupRequest.getEmail();
		this.password=signupRequest.getPassword();
		this.phoneNo=signupRequest.getPhoneNo();

		Role role=new Role();
		role.setRole(signupRequest.getRole());
		
		this.roles.add(role);
	}
	
	
}
