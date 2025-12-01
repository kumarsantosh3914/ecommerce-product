package com.santoshdev.ecommerce_backend.controller;

import com.santoshdev.ecommerce_backend.dto.AuthResponse;
import com.santoshdev.ecommerce_backend.dto.LoginRequest;
import com.santoshdev.ecommerce_backend.dto.MessageResponse;
import com.santoshdev.ecommerce_backend.dto.SignupRequest;
import com.santoshdev.ecommerce_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody SignupRequest signupRequest) {
        MessageResponse response = authService.registerUser(signupRequest);

        if (response.getMessage().startsWith("Error")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Optional<AuthResponse> authResponse = authService.authenticateUser(loginRequest);

        if (authResponse.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("Invalid email or password"), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(authResponse.get(), HttpStatus.OK);
    }
}



