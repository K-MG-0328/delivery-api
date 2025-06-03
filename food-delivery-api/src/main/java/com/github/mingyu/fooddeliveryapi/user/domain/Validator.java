package com.github.mingyu.fooddeliveryapi.user.domain;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    public static void validate(User user) {
        validate(user.getUserId(),
                user.getPassword(),
                user.getEmail(),
                user.getUserInfo(),
                user.getRole(),
                user.getStatus());
    }

    // 유저 데이터 검증
    public static void validate(String userId, EncodedPassword password, String email,
                                UserInfo userInfo, UserRole role, UserStatus status) {

        List<String> errors = new ArrayList<>();

        if (userId == null || userId.isBlank()) {
            errors.add("userId는 null이거나 비어있을 수 없습니다.");
        }

        if (password == null || password.getEncodedPassword() == null || password.getEncodedPassword().isBlank()) {
            errors.add("비밀번호는 null이거나 비어있을 수 없습니다.");
        }

        if (email == null || email.isBlank()) {
            errors.add("이메일은 null이거나 비어있을 수 없습니다.");
        }

        if (userInfo == null) {
            errors.add("userInfo는 null일 수 없습니다.");
        } else {
            if (userInfo.getName() == null || userInfo.getName().isBlank()) {
                errors.add("사용자 이름(name)은 null이거나 비어있을 수 없습니다.");
            }
            if (userInfo.getPhone() == null || userInfo.getPhone().isBlank()) {
                errors.add("전화번호(phone)는 null이거나 비어있을 수 없습니다.");
            }
            if (userInfo.getAddress() == null || userInfo.getAddress().isBlank()) {
                errors.add("주소는 null이거나 비어있을 수 없습니다.");
            }
        }

        if (role == null) {
            errors.add("역할(role)은 null일 수 없습니다.");
        }

        if (status == null) {
            errors.add("상태(status)는 null일 수 없습니다.");
        }

        if (!errors.isEmpty()) {
            throw new InvalidUserException(errors);
        }
    }
}
