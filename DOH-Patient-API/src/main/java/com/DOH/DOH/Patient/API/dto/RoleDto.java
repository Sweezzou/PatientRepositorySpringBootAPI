package com.DOH.DOH.Patient.API.dto;

import java.util.List;

import com.DOH.DOH.Patient.API.entity.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

	private String error;
    private String message;
	private String roleName;
	private Roles roles;
	private List<Roles> rolesList;
	
}
