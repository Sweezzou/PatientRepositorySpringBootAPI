package com.DOH.DOH.Patient.API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.DOH.DOH.Patient.API.dto.AuthDto;
import com.DOH.DOH.Patient.API.services.UserManagementService;

@RestController
public class AuthenticationController {

	@Autowired
	private UserManagementService usersManagementService;

	@PostMapping("/public/login")
	public ResponseEntity<AuthDto> login(@RequestBody AuthDto req) {
		return ResponseEntity.ok(usersManagementService.login(req));
	}

	@PostMapping("/public/register")
	public ResponseEntity<AuthDto> register(@RequestBody AuthDto reg) {
		return ResponseEntity.ok(usersManagementService.register(reg));
	}

	@GetMapping("/public/get-all-users")
	public ResponseEntity<AuthDto> getAllUsers() {
		return ResponseEntity.ok(usersManagementService.getAllUsers());
	}

	@GetMapping("/public/get-profile")
	public ResponseEntity<AuthDto> getMyProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		AuthDto response = usersManagementService.getMyInfo(email);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

}
