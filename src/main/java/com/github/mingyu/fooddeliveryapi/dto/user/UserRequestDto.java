package com.github.mingyu.fooddeliveryapi.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "회원 가입 또는 수정 요청")
public class UserRequestDto {

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "이메일 주소", example = "gildong@example.com")
    private String email;

    @Schema(description = "비밀번호", example = "password123")
    private String password;

    @Schema(description = "휴대폰 번호", example = "010-1234-5678")
    private String phone;

    @Schema(description = "역할", example = "USER")
    private String role;

    @Schema(description = "현재 주소", example = "서울특별시 강남구")
    private String currentAddress;

    @Schema(description = "상태", example = "ACTIVE")
    private String status;
}
