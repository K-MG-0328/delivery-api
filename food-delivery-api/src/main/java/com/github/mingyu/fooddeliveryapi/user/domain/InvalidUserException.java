package com.github.mingyu.fooddeliveryapi.user.domain;

import java.util.List;

public class InvalidUserException extends RuntimeException {
    private final List<String> errors;

    public InvalidUserException(List<String> errors) {
        super("입력 값에 오류가 존재합니다.");
        this.errors = errors;
}

public List<String> getErrors() {
        return errors;
    }
}
