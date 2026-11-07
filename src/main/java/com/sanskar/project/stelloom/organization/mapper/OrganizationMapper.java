package com.sanskar.project.stelloom.organization.mapper;

import com.sanskar.project.stelloom.organization.Organization;
import com.sanskar.project.stelloom.organization.dto.OrganizationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    OrganizationResponse toResponse(Organization organization);
}
