package com.jobportal.controller;


import com.jobportal.config.JwtUtil;
import com.jobportal.dto.AuthResponse;
import com.jobportal.dto.LoginRequest;
import com.jobportal.dto.RegisterRequest;
import com.jobportal.entity.User;
import com.jobportal.enums.UserRole;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository repo;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse(null, "User already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : UserRole.JOBSEEKER)
                .build();

        userService.saveUser(user);
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, "Registration Successful");
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
//        authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//        );
//
//        String token = jwtUtil.generateToken(request.getEmail());
//        return new AuthResponse(token, "Login Successful");
    	
    	String email=request.getEmail();
    	User user = repo.findByEmail(email)
    		    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

    	boolean status=passwordEncoder.matches(request.getPassword(),user.getPassword());
    	if(!status) {
    		throw new RuntimeException("invalid credentials");
    		
    	}
    	String token =jwtUtil.generateToken(email);
    	return new AuthResponse(token,"login success");
    }
    @GetMapping
    public String welcome() {
    	return "user module working fine";
    }
    
}
