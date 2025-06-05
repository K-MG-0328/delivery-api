package com.github.mingyu.fooddeliveryapi.user.domain.exception;

public class InactiveUserException extends RuntimeException {
    public InactiveUserException() {
        super("비활성화된 사용자입니다.");
    }
}
