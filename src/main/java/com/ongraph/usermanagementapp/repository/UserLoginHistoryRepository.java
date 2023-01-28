package com.ongraph.usermanagementapp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ongraph.usermanagementapp.entity.UserLoginHistory;

@Repository
public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, UUID> {

	
}
