package com.github.mingyu.fooddeliveryapi.cart.domain.exception;

import java.util.List;

public class InvalidCartException extends RuntimeException {

    private final List<String> errors;

    public InvalidCartException(List<String> errors) {
        super("입력 값에 오류가 존재합니다.");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
