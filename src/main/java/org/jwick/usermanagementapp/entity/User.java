package org.jwick.usermanagementapp.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper =false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends Audit {

	@Id
	@GeneratedValue(generator = "uuid")
	private UUID id;
	
	private String firstName;
	
	private String lastName;
	
	private String userName;
	
	private String email;
	
	private String password;
	
	private String phoneNo;
	
	private boolean enabled;
	
	@Column(length = 200)
	private String confirmationToken;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
	@Builder.Default
	private Set<Role> roles=new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	@Builder.Default
	private Set<UserLoginHistory>loginHistories=new HashSet<>();
	
	

}
