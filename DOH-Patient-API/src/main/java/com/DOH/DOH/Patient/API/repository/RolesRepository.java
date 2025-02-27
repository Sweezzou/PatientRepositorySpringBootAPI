package com.DOH.DOH.Patient.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DOH.DOH.Patient.API.entity.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer>{

	Optional<Roles> findByRoleName(String roleName);
	
}
