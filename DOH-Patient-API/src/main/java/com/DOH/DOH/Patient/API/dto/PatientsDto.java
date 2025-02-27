package com.DOH.DOH.Patient.API.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.DOH.DOH.Patient.API.entity.Patients;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientsDto {

	private String error;
	private String message;
	private String lastName;
	private String firstName;
	private String middleName;
	private String suffix;
	private String sex;
	private String civilStatus;
	private String placeOfBirth;
	private String address;
	private String bloodType;
	private String phoneNo;
	private Float height;
	private Float weight;
	private MultipartFile photo;
	private String photoUrl;
	private LocalDate birthdate;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private Patients patients;
	private List<Patients> patientsList;

	@JsonProperty
	private boolean deleted;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	@JsonProperty
	private boolean patientsStatus;

	public boolean isPatientsStatus() {
		return patientsStatus;
	}

	public void setPatientsStatus(boolean patientsStatus) {
		this.patientsStatus = patientsStatus;
	}

}
