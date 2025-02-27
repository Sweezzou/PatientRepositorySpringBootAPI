package com.DOH.DOH.Patient.API.controller;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DOH.DOH.Patient.API.dto.PatientsDto;
import com.DOH.DOH.Patient.API.entity.Patients;
import com.DOH.DOH.Patient.API.services.PatientsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class PatientsController {
	
	@Autowired
	private PatientsService patientsService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	public static final String UPLOAD_DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";
	
	@GetMapping("/public/get-all-patients")
    public ResponseEntity<PatientsDto> getAllEmployees(){
        return ResponseEntity.ok(patientsService.getAllPatints());
    }
	
	@PostMapping("/public/add-patient")
	public ResponseEntity<PatientsDto> addPatient(@RequestPart("patient") String patient, @RequestPart(value = "photo", required = false) MultipartFile photo) {
        try {
        	PatientsDto reg = objectMapper.readValue(patient, PatientsDto.class);
            reg.setPhoto(photo);
            return ResponseEntity.ok(patientsService.addPatient(reg));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "/public/image/{filename}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
	public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException {
	    return Files.readAllBytes(Paths.get(UPLOAD_DIRECTORY + filename));
	}
	
	@GetMapping("/public/get-patient/{id}")
    public ResponseEntity<PatientsDto> getPatientById(@PathVariable Long id){
        return ResponseEntity.ok(patientsService.getPatientById(id));
    }
	
	@PutMapping("/public/update-patient/{id}")
    public ResponseEntity<PatientsDto> updatePatient(@PathVariable Long id, @RequestPart("patient") String patient, @RequestPart(value = "photo", required = false) MultipartFile photo) {
        try {
            Patients updatedPatient = objectMapper.readValue(patient, Patients.class);
            return ResponseEntity.ok(patientsService.updatePatient(id, updatedPatient, photo));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@PutMapping("/public/update-patient-deleted/{id}")
    public ResponseEntity<PatientsDto> editPatientDeleted(@PathVariable Long id, @RequestBody PatientsDto req) {
        return ResponseEntity.ok(patientsService.updatePatientDeleted(id, req));        
    }

}
