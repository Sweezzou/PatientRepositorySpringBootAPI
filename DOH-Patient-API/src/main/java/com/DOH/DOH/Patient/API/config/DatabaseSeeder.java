package com.DOH.DOH.Patient.API.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.DOH.DOH.Patient.API.entity.Roles;
import com.DOH.DOH.Patient.API.entity.Users;
import com.DOH.DOH.Patient.API.repository.RolesRepository;
import com.DOH.DOH.Patient.API.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Component
public class DatabaseSeeder implements CommandLineRunner {

	private final RolesRepository rolesRepository;
	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;

	public DatabaseSeeder(RolesRepository rolesRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
		this.rolesRepository = rolesRepository;
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void run(String... args) {
		if (!rolesRepository.existsById(1)) {
			Roles adminRole = new Roles(null, "ADMIN");
			rolesRepository.save(adminRole);
			System.out.println("Inserted default ADMIN role.");
		}

		if (!usersRepository.existsById(1)) {
			Users adminAcct = new Users();
			adminAcct.setName("Admin");
			adminAcct.setEmail("admin@admin.com");
			adminAcct.setPassword(passwordEncoder.encode("123456"));
			adminAcct.setCreatedDate(LocalDateTime.now());
			adminAcct.setModifiedDate(LocalDateTime.now());
			Roles ADMIN = rolesRepository.findById(1).orElseThrow(() -> new RuntimeException("Role not found"));
			adminAcct.setRole(ADMIN);
			adminAcct.setUserStatus(true);
			usersRepository.save(adminAcct);
			System.out.println("Inserted default Admin account.");
		}
	}
}
