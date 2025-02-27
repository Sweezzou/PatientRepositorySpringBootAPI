package com.DOH.DOH.Patient.API.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "patients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patients {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, updatable = false)
	private Long id;
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
	private String photoUrl;
	

	@JsonProperty
	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean deleted;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthdate;

	@JsonProperty
	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean patientStatus;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
	private LocalDateTime modifiedDate;

	@CreatedBy
	@Column(nullable = false, updatable = false)
	private Integer createdBy;

	@LastModifiedBy
	@Column(insertable = false)
	private Integer modifiedBy;

}
