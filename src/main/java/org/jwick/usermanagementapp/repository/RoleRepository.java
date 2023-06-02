package org.jwick.usermanagementapp.repository;

import java.util.Optional;
import java.util.UUID;

import org.jwick.usermanagementapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import org.jwick.commonserviceapp.model.UserRoles;

public interface RoleRepository extends JpaRepository<Role, UUID>{

	Optional<Role> findByUserRole(UserRoles userRole);
}
