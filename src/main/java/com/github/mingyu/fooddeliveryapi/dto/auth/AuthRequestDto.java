package com.github.mingyu.fooddeliveryapi.dto.auth;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    private String email;
    private String password;
}
