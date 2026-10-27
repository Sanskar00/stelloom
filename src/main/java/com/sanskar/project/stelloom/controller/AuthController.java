package com.sanskar.project.stelloom.controller;



import com.sanskar.project.stelloom.dto.AuthResponse;
import com.sanskar.project.stelloom.dto.LoginRequest;
import com.sanskar.project.stelloom.dto.RegisterRequest;
import com.sanskar.project.stelloom.dto.UserResponse;
import com.sanskar.project.stelloom.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/register")
    public UserResponse post(@Valid @RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @PostMapping(path = "/login")
    public AuthResponse get(@Valid @RequestBody LoginRequest request){
        return authService.login(request);
    }

}
