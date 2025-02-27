package com.DOH.DOH.Patient.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DOH.DOH.Patient.API.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	Optional<Users> findByEmail(String email);

}
