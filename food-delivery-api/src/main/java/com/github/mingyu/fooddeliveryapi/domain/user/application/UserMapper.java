package com.github.mingyu.fooddeliveryapi.domain.user.application;

import com.github.mingyu.fooddeliveryapi.domain.user.domain.User;
import com.github.mingyu.fooddeliveryapi.domain.user.domain.dto.UserRequestDto;
import com.github.mingyu.fooddeliveryapi.domain.user.domain.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto dto);
    User updateFromDto(UserRequestDto dto, @MappingTarget User user);
    UserResponseDto toDto(User user);
}
