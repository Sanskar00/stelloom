package com.sanskar.project.stelloom.mapper;

import com.sanskar.project.stelloom.auth.dto.AuthResponse;
import com.sanskar.project.stelloom.user.dto.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface AuthMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "tokenType", constant = "Bearer")
    AuthResponse toAuthResponse(User user, String accessToken, long expiresInSeconds);
}