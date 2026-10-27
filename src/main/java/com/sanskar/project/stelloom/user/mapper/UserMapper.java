package com.sanskar.project.stelloom.user.mapper;

import com.sanskar.project.stelloom.user.User;
import com.sanskar.project.stelloom.user.dto.UserResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "avatarUrl", ignore = true)   // User has avatarKey, not avatarUrl
    UserResponse toResponse(User user);
}
