package com.github.mingyu.fooddeliveryapi.user.adapter.in.web.response;

import com.github.mingyu.fooddeliveryapi.user.domain.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "이메일 주소", example = "gildong@example.com")
    private String email;

    @Schema(description = "휴대폰 번호", example = "010-1234-5678")
    private String phone;

    @Schema(description = "역할", example = "USER")
    private UserRole role;

    @Schema(description = "현재 주소", example = "서울특별시 강남구")
    private String address;
}
