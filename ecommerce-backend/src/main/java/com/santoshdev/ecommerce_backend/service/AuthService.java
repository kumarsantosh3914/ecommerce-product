package com.santoshdev.ecommerce_backend.service;

import com.santoshdev.ecommerce_backend.dto.AuthResponse;
import com.santoshdev.ecommerce_backend.dto.LoginRequest;
import com.santoshdev.ecommerce_backend.dto.MessageResponse;
import com.santoshdev.ecommerce_backend.dto.SignupRequest;
import com.santoshdev.ecommerce_backend.model.User;
import com.santoshdev.ecommerce_backend.repository.UserRepository;
import com.santoshdev.ecommerce_backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public MessageResponse registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new MessageResponse("Error: Email is already in use!");
        }

        User user = new User();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(User.Role.USER);

        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    public Optional<AuthResponse> authenticateUser(LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return Optional.empty();
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        AuthResponse response = new AuthResponse(
                token,
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        );

        return Optional.of(response);
    }
}
