package com.sanskar.project.stelloom.user;

import com.sanskar.project.stelloom.auth.dto.AuthUser;
import com.sanskar.project.stelloom.user.dto.UserRequest;
import com.sanskar.project.stelloom.user.dto.UserResponse;
import com.sanskar.project.stelloom.user.mapper.UserMapper;
import io.jsonwebtoken.Jwt;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/user")
public class UserController {

    private final  UserRepository userRepository;
    private  final UserMapper userMapper;
    private  final UserService userService;

    @GetMapping(path = "/me")
    public UserResponse get(@AuthenticationPrincipal AuthUser principal){
        User user=userRepository.findByOrganizationIdAndId(principal.organizationId(), principal.id()).orElseThrow();
        return userMapper.toResponse(user);
    }

    @PatchMapping(path = "/me")
    public UserResponse patch(@AuthenticationPrincipal AuthUser principal, @RequestBody UserRequest request){
        return userService.updateProfile(principal.organizationId(),principal.id(), request);
    }

    @GetMapping(path = "/{userName}")
    public UserResponse get(@PathVariable String userName) {
        // Fetch and return the user
        return userService.getUserProfile(userName);
    }
}
