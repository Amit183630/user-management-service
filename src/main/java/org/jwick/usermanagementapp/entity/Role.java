package org.jwick.usermanagementapp.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jwick.commonserviceapp.model.UserRoles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	@Id
	@GeneratedValue(generator = "uuid")
	@JsonIgnore
	private UUID id;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20,name = "name",unique = true)
	private UserRoles userRole;
	
}
