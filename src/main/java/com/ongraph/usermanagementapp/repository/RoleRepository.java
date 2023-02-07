package com.ongraph.usermanagementapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ongraph.commonserviceapp.model.UserRoles;
import com.ongraph.usermanagementapp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, UUID>{

	Optional<Role> findByUserRole(UserRoles userRole);
}
