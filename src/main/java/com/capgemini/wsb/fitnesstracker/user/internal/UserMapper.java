package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.dto.UserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting between User entities and DTOs.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts a {@link User} entity to a {@link UserDto}.
     *
     * @param user the user entity to convert
     * @return the converted {@link UserDto}
     */
    UserDto toDto(User user);

    /**
     * Converts a {@link UserUpdateDto} to a {@link User} entity, ignoring the ID.
     *
     * @param userUpdateDto the user update DTO to convert
     * @return the converted {@link User} entity
     */
    @Mapping(target = "id", ignore = true)
    User toEntity(UserUpdateDto userUpdateDto);
}


