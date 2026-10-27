package com.sanskar.project.stelloom.auth.mapper;

import com.sanskar.project.stelloom.auth.dto.AuthResponse;
import com.sanskar.project.stelloom.user.User;
import com.sanskar.project.stelloom.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface AuthMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "tokenType", constant = "Bearer")
    AuthResponse toAuthResponse(User user, String accessToken, long expiresInSeconds);
}