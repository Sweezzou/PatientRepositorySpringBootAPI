package com.DOH.DOH.Patient.API.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DOH.DOH.Patient.API.dto.RoleDto;
import com.DOH.DOH.Patient.API.entity.Roles;
import com.DOH.DOH.Patient.API.repository.RolesRepository;

@Service
public class RoleService {

	@Autowired
	private RolesRepository rolesRepository;

	public RoleDto addRole(RoleDto addRoleRequest) {
		RoleDto resp = new RoleDto();

		try {
			Optional<Roles> existingRole = rolesRepository.findByRoleName(addRoleRequest.getRoleName());
			if (existingRole.isPresent()) {
				resp.setMessage("Role already exists");
				return resp;
			}

			Roles roleName = new Roles();
			roleName.setRoleName(addRoleRequest.getRoleName());

			Roles roleResult = rolesRepository.save(roleName);
			if (roleResult.getId() > 0) {
				resp.setRoles(roleResult);
				resp.setMessage("Saved successfully");
			}
		} catch (Exception e) {
			resp.setError(e.getMessage());
		}
		return resp;
	}

	public RoleDto getAllRoles() {
		RoleDto roleDto = new RoleDto();

		try {
			List<Roles> result = rolesRepository.findAll();
			if (!result.isEmpty()) {
				roleDto.setRolesList(result);
				roleDto.setMessage("Successful");
			} else {
				roleDto.setMessage("Not found");
			}
			return roleDto;
		} catch (Exception e) {
			roleDto.setMessage("Error: " + e.getMessage());
			return roleDto;
		}
	}

}
