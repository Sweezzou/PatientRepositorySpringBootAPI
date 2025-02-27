package com.DOH.DOH.Patient.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DOH.DOH.Patient.API.entity.Patients;

public interface PatientsRepository extends JpaRepository<Patients, Long> {
	Optional<Patients> findById(Integer id);
}
