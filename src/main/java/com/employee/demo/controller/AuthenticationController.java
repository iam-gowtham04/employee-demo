package com.employee.demo.controller;

import com.employee.demo.dto.EmployeeLoginRequest;
import com.employee.demo.dto.EmployeeRegisterRequest;
import com.employee.demo.model.User;
import com.employee.demo.repository.UserRepository;
import com.employee.demo.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {



        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private JwtService jwtService;

        @Autowired
        private AuthenticationManager authenticationManager;

        @PostMapping("/register")
        public String register(@RequestBody EmployeeRegisterRequest request) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());
            userRepository.save(user);
            return "User registered successfully!";
        }

        @PostMapping("/login")
        public Map<String, Object> login(@RequestBody EmployeeLoginRequest request) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtService.generateToken(user.getUsername(), user.getRole());
            System.out.println("Generated JWT: " + token);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("tokenType", "Bearer");
            return response;
        }

    }

