package com.sanskar.project.stelloom.mapper;

import com.sanskar.project.stelloom.dto.UserResponse;
import com.sanskar.project.stelloom.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "avatarUrl", ignore = true)   // User has avatarKey, not avatarUrl
    UserResponse toResponse(User user);
}
