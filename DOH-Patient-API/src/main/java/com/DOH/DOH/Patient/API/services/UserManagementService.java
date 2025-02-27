package com.DOH.DOH.Patient.API.services;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.DOH.DOH.Patient.API.dto.AuthDto;
import com.DOH.DOH.Patient.API.entity.Roles;
import com.DOH.DOH.Patient.API.entity.Users;
import com.DOH.DOH.Patient.API.repository.RolesRepository;
import com.DOH.DOH.Patient.API.repository.UsersRepository;

@Service
public class UserManagementService {

	@Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private JWTUtils jwtUtils;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private RolesRepository rolesRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    	
    public AuthDto login(AuthDto loginRequest) {
        AuthDto response = new AuthDto();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                    loginRequest.getPassword()));
            var userOpt = usersRepository.findByEmail(loginRequest.getEmail());
            
            if (userOpt.isEmpty()) {
                throw new RuntimeException("User not found");
            }
            
            var user = userOpt.get();
            
            if (!user.getUserStatus()) {
                throw new RuntimeException("User account is not active");
            }
            
            String role = user.getRole().getRoleName();

            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRoleName(role);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    public AuthDto register(AuthDto registrationRequest) {
        AuthDto resp = new AuthDto();

        try {
            Optional<Users> existingUser = usersRepository.findByEmail(registrationRequest.getEmail());
            if (existingUser.isPresent()) {
                resp.setStatusCode(409);
                resp.setMessage("Email is already in use");
                return resp;
            }
            
            if (registrationRequest.getRoleName() == null) {
                resp.setStatusCode(400);
                resp.setMessage("Role is required");
                return resp;
            }

            Roles role = rolesRepository.findByRoleName(registrationRequest.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            Users user = new Users();
            user.setEmail(registrationRequest.getEmail());
            user.setName(registrationRequest.getName());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setUserStatus(registrationRequest.getUserStatus());
            user.setRole(role);

            Users usersResult = usersRepository.save(user);

            if (registrationRequest.getUserStatus() == true) {
                resp.setStatusCode(400);
                resp.setMessage("test");
                return resp;
            }
            
            if (Optional.ofNullable(usersResult.getId()).isPresent() && usersResult.getId() > 0) {
                resp.setUsers(usersResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            } else {
                resp.setStatusCode(500);
                resp.setMessage("Failed to save user");
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }
    
    public AuthDto getAllUsers() {
        AuthDto reqRes = new AuthDto();

        try {
            List<Users> result = usersRepository.findAll();
            if (!result.isEmpty()) {
                reqRes.setUsersList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }
    
    public AuthDto getMyInfo(String email){
        AuthDto reqRes = new AuthDto();
        try {
            Optional<Users> userOptional = usersRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setUsers(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;
    }
    
}
