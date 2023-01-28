package com.ongraph.usermanagementapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ongraph.usermanagementapp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

	boolean existsByUserName(String userName);
	
	Optional<User> findByUserName(String userName);
	
}
