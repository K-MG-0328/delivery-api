package com.github.mingyu.fooddeliveryapi.user.adapter.in.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "회원 정보 수정 요청")
public class UserUpdateRequest {

    @Schema(description = "이름", example = "uuid")
    private final String userId;

    @Schema(description = "이름", example = "홍길동")
    private final String name;

    @Schema(description = "이메일 주소", example = "gildong@example.com")
    private final String email;

    @Schema(description = "비밀번호", example = "password123")
    private final String password;

    @Schema(description = "휴대폰 번호", example = "010-1234-5678")
    private final String phone;

    @Schema(description = "현재 주소", example = "서울특별시 강남구")
    private final String address;
}
