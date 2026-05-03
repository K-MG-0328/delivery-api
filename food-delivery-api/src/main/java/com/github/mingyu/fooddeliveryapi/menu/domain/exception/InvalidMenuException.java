package com.github.mingyu.fooddeliveryapi.menu.domain.exception;

import java.util.List;

public class InvalidMenuException extends RuntimeException {

    private final List<String> errors;

    public InvalidMenuException(List<String> errors) {
        super("입력 값에 오류가 존재합니다.");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
