package org.jwick.usermanagementapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.jwick.usermanagementapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

	boolean existsByUserName(String userName);
	
	Optional<User> findByUserName(String userName);
	
	Optional<User> findByConfirmationToken(String confirmationToken);
}
