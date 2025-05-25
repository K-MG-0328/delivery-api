package com.github.mingyu.fooddeliveryapi.domain.user.application;

import com.github.mingyu.fooddeliveryapi.domain.user.domain.User;
import com.github.mingyu.fooddeliveryapi.domain.user.presentation.dto.UserRequest;
import com.github.mingyu.fooddeliveryapi.domain.user.presentation.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest dto);
    User updateFromDto(UserRequest dto, @MappingTarget User user);
    UserResponse toDto(User user);
}
