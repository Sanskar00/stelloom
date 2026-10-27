package com.sanskar.project.stelloom.service;

import com.sanskar.project.stelloom.auth.dto.AuthResponse;
import com.sanskar.project.stelloom.auth.dto.LoginRequest;
import com.sanskar.project.stelloom.auth.dto.RegisterRequest;
import com.sanskar.project.stelloom.user.dto.UserResponse;
import com.sanskar.project.stelloom.user.dto.User;
import com.sanskar.project.stelloom.exception.DuplicateResourceException;
import com.sanskar.project.stelloom.mapper.AuthMapper;
import com.sanskar.project.stelloom.mapper.UserMapper;
import com.sanskar.project.stelloom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthMapper authMapper;

    public UserResponse register(RegisterRequest request){
        String username = request.username().trim().toLowerCase();
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByUsername(username)) {
            throw new DuplicateResourceException("Username already taken");
        }
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email already registered");
        }
        User user = User.builder()
                .username(request.username())
                .email(request.email().toLowerCase())
                .password(passwordEncoder.encode(request.password()))   // hashed here
                .fullName(request.fullName())
                .build();

        User saved=userRepository.save(user);

        return userMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String username = request.username().trim().toLowerCase();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        String token= jwtService.generateToken(user);

        return authMapper.toAuthResponse(user,token, jwtService.getExpirationSeconds());
    }
}
