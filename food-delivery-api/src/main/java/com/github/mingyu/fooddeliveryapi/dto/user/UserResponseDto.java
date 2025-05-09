package com.github.mingyu.fooddeliveryapi.dto.user;

import com.github.mingyu.fooddeliveryapi.enums.UserRole;
import com.github.mingyu.fooddeliveryapi.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "이메일 주소", example = "gildong@example.com")
    private String email;

    @Schema(description = "휴대폰 번호", example = "010-1234-5678")
    private String phone;

    @Schema(description = "역할", example = "USER")
    private UserRole role;

    @Schema(description = "현재 주소", example = "서울특별시 강남구")
    private String currentAddress;

    @Schema(description = "상태", example = "ACTIVE")
    private UserStatus status;
}
