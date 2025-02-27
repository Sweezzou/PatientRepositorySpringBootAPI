package com.DOH.DOH.Patient.API.services;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.DOH.DOH.Patient.API.dto.PatientsDto;
import com.DOH.DOH.Patient.API.entity.Patients;
import com.DOH.DOH.Patient.API.repository.PatientsRepository;

@Service
public class PatientsService {
	
	@Autowired
	private PatientsRepository patientsRepository;
	
	public static final String UPLOAD_DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";
	
	public PatientsDto getAllPatints() {
		PatientsDto reqRes = new PatientsDto();
        try {
            List<Patients> result = patientsRepository.findAll();
            if (!result.isEmpty()) {
                reqRes.setPatientsList(result);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setMessage("No patients found");	
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }
	
	public PatientsDto addPatient(PatientsDto addPatientRequest) {
		PatientsDto resp = new PatientsDto();

        try {
            Patients patient = new Patients();
            patient.setLastName(addPatientRequest.getLastName());
            patient.setFirstName(addPatientRequest.getFirstName());
            patient.setMiddleName(addPatientRequest.getMiddleName());
            patient.setSuffix(addPatientRequest.getSuffix());           		
            patient.setSex(addPatientRequest.getSex());
            patient.setCivilStatus(addPatientRequest.getCivilStatus());
            patient.setPlaceOfBirth(addPatientRequest.getPlaceOfBirth());
            patient.setAddress(addPatientRequest.getAddress());
            patient.setBloodType(addPatientRequest.getBloodType());
            patient.setPhoneNo(addPatientRequest.getPhoneNo());
            patient.setPhotoUrl(addPatientRequest.getPhotoUrl());
            patient.setBirthdate(addPatientRequest.getBirthdate());
            patient.setPatientStatus(addPatientRequest.isPatientsStatus());
            patient.setDeleted(addPatientRequest.isDeleted());

            if (addPatientRequest.getPhoto() != null && !addPatientRequest.getPhoto().isEmpty()) {
                String photoUrl = savePhoto(addPatientRequest.getPhoto());
                patient.setPhotoUrl(photoUrl);
            }
            
            Patients patientResult = patientsRepository.save(patient);
            if (Optional.ofNullable(patientResult.getId()).isPresent() && patientResult.getId() > 0) {
                resp.setPatients(patientResult);
                resp.setMessage("Saved Successfully");
            } else {
                resp.setMessage("Save failed");
            }

        } catch (Exception e) {
            resp.setError(e.getMessage());
        }
        return resp;
    }
	
	private String savePhoto(MultipartFile photo) throws IOException {
	    if (!new File(UPLOAD_DIRECTORY).exists()) {
	        Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
	    }
	    String filename = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
	    
	    try {
	        Path fileStorageLocation = Paths.get(UPLOAD_DIRECTORY).toAbsolutePath().normalize();
	        if (!Files.exists(fileStorageLocation)) {
	            Files.createDirectories(fileStorageLocation);
	        }
	        Files.copy(photo.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);
	        return filename;

	    } catch (Exception exception) {
	        throw new RuntimeException("Unable to save image", exception);
	    }
	}
	
	public PatientsDto getPatientById(Long id) {
		PatientsDto reqRes = new PatientsDto();
        try {
            Patients patientById = patientsRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
            reqRes.setPatients(patientById);
            reqRes.setMessage("id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setMessage("Error: " + e.getMessage());
        }
        return reqRes;
    }
	
	public PatientsDto updatePatient(Long id, Patients updatedPatient, MultipartFile photo) {
        PatientsDto patientDto = new PatientsDto();
        try {
            Optional<Patients> patientOptional = patientsRepository.findById(id);
            if (patientOptional.isPresent()) {
                Patients existingPatient = patientOptional.get();
                existingPatient.setLastName(updatedPatient.getLastName());
                existingPatient.setFirstName(updatedPatient.getFirstName());
                existingPatient.setMiddleName(updatedPatient.getMiddleName());
                existingPatient.setSuffix(updatedPatient.getSuffix());           		
                existingPatient.setSex(updatedPatient.getSex());
                existingPatient.setCivilStatus(updatedPatient.getCivilStatus());
                existingPatient.setPlaceOfBirth(updatedPatient.getPlaceOfBirth());
                existingPatient.setAddress(updatedPatient.getAddress());
                existingPatient.setBloodType(updatedPatient.getBloodType());
                existingPatient.setPhoneNo(updatedPatient.getPhoneNo());
                existingPatient.setPhotoUrl(updatedPatient.getPhotoUrl());
                existingPatient.setBirthdate(updatedPatient.getBirthdate());
                existingPatient.setPatientStatus(updatedPatient.isPatientStatus());
                existingPatient.setDeleted(updatedPatient.isDeleted());
                
                if (photo != null && !photo.isEmpty()) {
                    String photoUrl = savePhoto(photo);
                    existingPatient.setPhotoUrl(photoUrl);
                } else {
                	existingPatient.setPhotoUrl(updatedPatient.getPhotoUrl());
                }
                
                Patients savedPatient = patientsRepository.save(existingPatient);
                patientDto.setPatients(savedPatient);
                patientDto.setMessage("Updated successfully");
            } else {
            	patientDto.setMessage("Not found");
            }
        } catch (Exception e) {
        	patientDto.setMessage("Error: " + e.getMessage());
        }
        return patientDto;
    }
	
	public PatientsDto updatePatientDeleted(Long id, PatientsDto updatePatientDeletedRequest) {
		PatientsDto resp = new PatientsDto();
		
		try {
			Patients patient = patientsRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Patient not Found"));
			
			patient.setDeleted(updatePatientDeletedRequest.isDeleted());
			
			Patients patientResult = patientsRepository.save(patient);
			if (Optional.ofNullable(patientResult.getId()).isPresent() && patientResult.getId() > 0) {
				resp.setPatients(patientResult);
				resp.setMessage("Deleted successfully");
			} else {
				resp.setMessage("Delete failed");
			}
		} catch (Exception e) {
			resp.setError(e.getMessage());
		}
		return resp;
	}

}
