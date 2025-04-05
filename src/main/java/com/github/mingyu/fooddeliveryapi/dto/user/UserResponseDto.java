package com.github.mingyu.fooddeliveryapi.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
    private String currentAddress;
    private String status;
}
