package org.jwick.usermanagementapp.repository;

import java.util.UUID;

import org.jwick.usermanagementapp.entity.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, UUID> {

	
}
