package com.github.mingyu.fooddeliveryapi.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    @Schema(description = "토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnaWxkb25nQGV4YW1wbGUuY....")
    private String token;
    @Schema(description = "이메일", example = "gildong@example.com")
    private String email;
    @Schema(description = "유저 ID", example = "1")
    private Long userId;
}
