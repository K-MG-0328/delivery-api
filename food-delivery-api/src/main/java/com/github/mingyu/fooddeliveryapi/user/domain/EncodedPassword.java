package com.github.mingyu.fooddeliveryapi.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class EncodedPassword {

    protected EncodedPassword(){};

    @Column(nullable = false)
    private String encodedPassword;

    private EncodedPassword(String value) {
        this.encodedPassword = value;
    }

    public static EncodedPassword fromEncoded(String encodedPassword) {
        if (!isBcryptFormat(encodedPassword)) {
            throw new IllegalArgumentException("잘못된 암호화 형식의 비밀번호입니다.");
        }
        return new EncodedPassword(encodedPassword);
    }

    private static boolean isBcryptFormat(String value) {
        // Bcrypt는 보통 $2a$ / $2b$ / $2y$ 로 시작하고 길이는 60자
        return value != null && value.matches("^\\$2[ayb]\\$.{56}$");
    }
}
