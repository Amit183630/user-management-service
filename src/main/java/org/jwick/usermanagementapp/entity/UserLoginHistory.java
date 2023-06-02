package org.jwick.usermanagementapp.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_login_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginHistory {

	@Id
	@GeneratedValue(generator = "uuid")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private LocalDateTime logInAttemptTime;
	
	private String ipAddress;
	
	private boolean loggedIn;
}
