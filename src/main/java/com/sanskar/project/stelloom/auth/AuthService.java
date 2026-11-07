package com.sanskar.project.stelloom.auth;

import com.sanskar.project.stelloom.auth.dto.AuthResponse;
import com.sanskar.project.stelloom.auth.dto.LoginRequest;
import com.sanskar.project.stelloom.auth.dto.RegisterRequest;
import com.sanskar.project.stelloom.common.exception.enums.OrganizationStatus;
import com.sanskar.project.stelloom.common.exception.enums.Role;
import com.sanskar.project.stelloom.organization.Organization;
import com.sanskar.project.stelloom.organization.OrganizationRepository;
import com.sanskar.project.stelloom.user.User;
import com.sanskar.project.stelloom.user.dto.UserResponse;
import com.sanskar.project.stelloom.common.exception.DuplicateResourceException;
import com.sanskar.project.stelloom.auth.mapper.AuthMapper;
import com.sanskar.project.stelloom.user.mapper.UserMapper;
import com.sanskar.project.stelloom.user.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Builder
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthMapper authMapper;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public UserResponse register(RegisterRequest request){

        // 1. Save the organization first to generate its ID
        Organization org = organizationRepository.save(Organization.builder()
                .name(request.organizationName().trim())
                .slug(uniqueSlug(request.organizationName()))
                .build());

        // 2. Build and save the User, associating it with the Organization
        User user = User.builder()
                .username(request.username())
                .email(request.email().toLowerCase())
                .password(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .organization(org)
                .role(Role.ORG_ADMIN)
                .build();

        User savedUser = userRepository.save(user);

        organizationRepository.save(org);

        return userMapper.toResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String username = request.username().trim().toLowerCase();
        String slug=request.workspace().trim().toLowerCase();
        User user = userRepository.findByOrganizationSlugAndUsername(slug,username)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));


        if (!passwordEncoder.matches(request.password(), user.getPassword())
                || user.getOrganization().getStatus() != OrganizationStatus.ACTIVE) {
            throw new BadCredentialsException("Invalid credentials");
        }
        String token= jwtService.generateToken(user);

        return authMapper.toAuthResponse(user,token, jwtService.getExpirationSeconds());
    }

    private String uniqueSlug(String name) {
        String base = name.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        if (base.isBlank()) base = "org";
        String slug = base;
        int i = 2;
        while (organizationRepository.existsBySlug(slug)) {
            slug = base + "-" + i++;
        }
        return slug;
    }
}
