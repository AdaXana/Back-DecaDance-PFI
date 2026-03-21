package com.decadance.Back_DecaDance_PFI.mapper;

import com.decadance.Back_DecaDance_PFI.dto.request.UserRequestDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.UserResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "played", constant = "0")
    @Mapping(target = "victories", constant = "0")
    @Mapping(target = "successes", constant = "0")
    @Mapping(target = "fails", constant = "0")
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponse(User entity);
}