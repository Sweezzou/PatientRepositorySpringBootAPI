package com.DOH.DOH.Patient.API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.DOH.DOH.Patient.API.dto.AuthDto;
import com.DOH.DOH.Patient.API.dto.RoleDto;
import com.DOH.DOH.Patient.API.services.RoleService;

@RestController
public class RoleController {
	
	@Autowired
    private RoleService roleService;
	
	@PostMapping("/public/add-role")
    public ResponseEntity<RoleDto> addRole(@RequestBody RoleDto reg){
        return ResponseEntity.ok(roleService.addRole(reg));
    }
	
	@GetMapping("/public/get-all-roles")
    public ResponseEntity<RoleDto> getAllRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }

}
