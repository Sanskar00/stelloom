package com.sanskar.project.stelloom.user.mapper;

import com.sanskar.project.stelloom.organization.mapper.OrganizationMapper;
import com.sanskar.project.stelloom.user.User;
import com.sanskar.project.stelloom.user.dto.UserResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface UserMapper {

    @Mapping(target = "avatarUrl", ignore = true)   // User has avatarKey, not avatarUrl
    UserResponse toResponse(User user);
}
