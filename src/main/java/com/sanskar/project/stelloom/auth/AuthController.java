package com.sanskar.project.stelloom.auth;



import com.sanskar.project.stelloom.auth.dto.AuthResponse;
import com.sanskar.project.stelloom.auth.dto.LoginRequest;
import com.sanskar.project.stelloom.auth.dto.RegisterRequest;
import com.sanskar.project.stelloom.user.dto.UserResponse;
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
