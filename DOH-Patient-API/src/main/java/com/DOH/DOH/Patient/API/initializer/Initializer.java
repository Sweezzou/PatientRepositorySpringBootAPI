package com.DOH.DOH.Patient.API.initializer;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.DOH.DOH.Patient.API.entity.Roles;
import com.DOH.DOH.Patient.API.entity.Users;
import com.DOH.DOH.Patient.API.repository.RolesRepository;
import com.DOH.DOH.Patient.API.repository.UsersRepository;

import jakarta.annotation.PostConstruct;

@Component
public class Initializer {

	private final RolesRepository rolesRepository;
	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;

    @Autowired
    public Initializer(RolesRepository rolesRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.rolesRepository = rolesRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

	@PostConstruct
	public void initialize() {
//		initializeRoles(); 
//		initializeAdminAccount();
	}

	private void initializeRoles() {

		if (!rolesRepository.existsById(1)) {
            Roles userRole = new Roles();
            userRole.setId(1);
            userRole.setRoleName("ADMIN");
            rolesRepository.save(userRole);
        }
	}
	
	private void initializeAdminAccount() {
		if (!usersRepository.existsById(1)) {
			Users adminAcct = new Users();
			adminAcct.setId(1);
			adminAcct.setName("admin");
			adminAcct.setEmail("admin@admin.com");
			adminAcct.setPassword(passwordEncoder.encode("P@ssw0rd@1cthr1s"));
			adminAcct.setCreatedDate(LocalDateTime.now());
			adminAcct.setModifiedDate(LocalDateTime.now());
			
            Roles ADMIN = rolesRepository.findById(1).orElseThrow(() -> new RuntimeException("Role not found"));

            adminAcct.setRole(ADMIN);
            adminAcct.setUserStatus(true);
			usersRepository.save(adminAcct);
		}
		
	}
	
}
