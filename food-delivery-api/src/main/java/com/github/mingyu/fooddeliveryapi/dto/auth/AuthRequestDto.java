package com.github.mingyu.fooddeliveryapi.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    @Schema(description = "이메일", example = "gildong@example.com")
    private String email;
    @Schema(description = "패스워드", example = "password123")
    private String password;
}
